package org.positive.daymotion.presentation.my.fragment

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.FragmentMyTabBinding
import org.positive.daymotion.presentation.common.ScrollableFragment
import org.positive.daymotion.presentation.common.adapter.FeedThumbnailAdapter
import org.positive.daymotion.presentation.common.base.BaseFragment
import org.positive.daymotion.presentation.common.base.viewModelOf
import org.positive.daymotion.presentation.common.extension.registerActivityResult
import org.positive.daymotion.presentation.my.activity.MyProfileEditActivity
import org.positive.daymotion.presentation.my.model.UserProfileViewData
import org.positive.daymotion.presentation.my.viewmodel.MyTabViewModel
import org.positive.daymotion.presentation.upload.activity.FeedUploadActivity

@AndroidEntryPoint
class MyTabFragment : BaseFragment<FragmentMyTabBinding>(R.layout.fragment_my_tab),
    ScrollableFragment {

    private val viewModel by viewModelOf<MyTabViewModel>()
    private val handler by lazy { Handler() }
    private val feedThumbnailAdapter by lazy { FeedThumbnailAdapter() }

    private val myProfileEditActivityLauncher = registerActivityResult {
        val data = it.data
        val resultCode = it.resultCode
        if (data != null && resultCode == RESULT_OK) {
            data.getParcelableExtra<UserProfileViewData>("userProfileViewData")?.let { profile ->
                viewModel.updateProfile(profile)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.handler = handler

        setupViews()
        setupObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadMyProfileAndFeeds()
    }

    private fun setupViews() {
        binding.myFeedRecyclerView.apply {
            adapter = feedThumbnailAdapter
            layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        }
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, offset ->
            val total = appBarLayout.totalScrollRange
            binding.profileContainer.alpha = (total + offset) / total.toFloat()
        })
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
        fun goToProfileEdit(
            userProfileViewData: UserProfileViewData
        ) = MyProfileEditActivity.startForResult(
            requireContext(),
            myProfileEditActivityLauncher,
            userProfileViewData
        )

        fun startFeedUploadActivity() = FeedUploadActivity.start(requireContext())
    }
}