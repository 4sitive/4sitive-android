package org.positive.daymotion.presentation.category.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.FragmentCategoryBrowserPageBinding
import org.positive.daymotion.presentation.base.BaseFragment
import org.positive.daymotion.presentation.base.util.viewModelOf
import org.positive.daymotion.presentation.category.activity.CategoryDetailActivity
import org.positive.daymotion.presentation.category.adapter.CategoryBrowserAdapter
import org.positive.daymotion.presentation.category.viewmodel.CategoryBrowserPageViewModel
import org.positive.daymotion.presentation.common.ScrollableFragment

@AndroidEntryPoint
class CategoryBrowserPageFragment :
    BaseFragment<FragmentCategoryBrowserPageBinding>(R.layout.fragment_category_browser_page),
    ScrollableFragment {

    private val viewModel by viewModelOf<CategoryBrowserPageViewModel>()
    private val handler by lazy { Handler() }
    private val categoryBrowserAdapter by lazy { CategoryBrowserAdapter(handler) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupObservers()

        viewModel.loadMissionHistories()
    }

    override fun scrollToTop() {
        binding.categoryBrowserRecyclerView.smoothScrollToPosition(0)
    }

    private fun setupViews() {
        binding.categoryBrowserRecyclerView.apply {
            adapter = categoryBrowserAdapter
            layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        }
    }

    private fun setupObservers() {
        viewModel.categoryBrowserItems.observeNonNull {
            categoryBrowserAdapter.replaceAll(it)
        }
    }

    inner class Handler {
        fun goToCategoryDetail(title: String) =
            CategoryDetailActivity.start(requireContext(), title)
    }
}