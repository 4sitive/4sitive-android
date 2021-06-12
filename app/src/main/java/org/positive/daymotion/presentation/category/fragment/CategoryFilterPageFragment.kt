package org.positive.daymotion.presentation.category.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.FragmentCategoryFilterPageBinding
import org.positive.daymotion.presentation.base.BaseFragment
import org.positive.daymotion.presentation.base.util.viewModelOf
import org.positive.daymotion.presentation.category.adapter.CategoryFilterAdapter
import org.positive.daymotion.presentation.category.viewmodel.CategoryFilterPageViewModel

@AndroidEntryPoint
class CategoryFilterPageFragment :
    BaseFragment<FragmentCategoryFilterPageBinding>(R.layout.fragment_category_filter_page) {

    private val viewModel by viewModelOf<CategoryFilterPageViewModel>()
    private val categoryFilterAdapter by lazy { CategoryFilterAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupObservers()

        viewModel.loadMissionHistories()
    }

    private fun setupViews() {
        binding.categoryFilterRecyclerView.apply {
            adapter = categoryFilterAdapter
            layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        }
    }

    private fun setupObservers() {
        viewModel.categoryFilters.observeNonNull {
            categoryFilterAdapter.replaceAll(it)
        }
    }
}