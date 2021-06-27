package org.positive.daymotion.presentation.my.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.FragmentMyTabBinding
import org.positive.daymotion.presentation.common.ScrollableFragment
import org.positive.daymotion.presentation.common.adapter.FeedThumbnailAdapter
import org.positive.daymotion.presentation.common.base.BaseFragment
import org.positive.daymotion.presentation.common.base.viewModelOf
import org.positive.daymotion.presentation.common.extension.registerActivityResult
import org.positive.daymotion.presentation.my.activity.MyProfileEditActivity
import org.positive.daymotion.presentation.my.viewmodel.MyTabViewModel

@AndroidEntryPoint
class MyTabFragment : BaseFragment<FragmentMyTabBinding>(R.layout.fragment_my_tab),
    ScrollableFragment {

    private val viewModel by viewModelOf<MyTabViewModel>()
    private val handler by lazy { Handler() }
    private val feedThumbnailAdapter by lazy { FeedThumbnailAdapter() }

    private val myProfileEditActivityLauncher = registerActivityResult {
        // TODO(yh): reload updated profile
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.handler = handler

        setupViews()
        setupObservers()

        viewModel.loadMyFeeds()
    }

    private fun setupViews() {
        binding.myFeedRecyclerView.apply {
            adapter = feedThumbnailAdapter
            layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        }
    }

    private fun setupObservers() {
        viewModel.myFeedsThumbnails.observeNonNull {
            feedThumbnailAdapter.replaceAll(it)
        }
    }

    override fun scrollToTop() {
        binding.myFeedRecyclerView.smoothScrollToPosition(0)
    }

    inner class Handler {
        fun goToProfileEdit() = MyProfileEditActivity.startForResult(
            requireContext(),
            myProfileEditActivityLauncher,
            "",
            "시루",
            "시루랑 함께하는 일상"
        )
    }
}