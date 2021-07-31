package org.positive.daymotion.presentation.home.activity

import android.content.Context
import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityPostListBinding
import org.positive.daymotion.presentation.common.base.BaseActivity
import org.positive.daymotion.presentation.common.base.viewModelOf
import org.positive.daymotion.presentation.common.bundle
import org.positive.daymotion.presentation.common.extension.startWith
import org.positive.daymotion.presentation.home.model.MissionViewItem
import org.positive.daymotion.presentation.home.viewmodel.PostListViewModel
import org.positive.daymotion.presentation.upload.activity.FeedUploadActivity

@AndroidEntryPoint
class PostListActivity :
    BaseActivity<ActivityPostListBinding>(R.layout.activity_post_list) {

    private val viewModel by viewModelOf<PostListViewModel>()
    private val missionViewItem by bundle<MissionViewItem>()
    private val handler by lazy { Handler() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.missionViewItem = missionViewItem
        binding.handler = handler

        setupViews()
    }

    private fun setupViews() {
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, offset ->
            val total = appBarLayout.totalScrollRange
            val percentage = (total + offset) / total.toFloat()
            binding.imageViewContainer.alpha = percentage
            binding.homeMissionCard.alpha = percentage
            binding.toolbar.alpha = 1 - percentage
        })
    }

    inner class Handler {
        fun finish() = this@PostListActivity.finish()
        fun startFeedUploadActivity() = FeedUploadActivity.start(this@PostListActivity)
    }

    companion object {
        fun start(
            context: Context,
            missionViewItem: MissionViewItem
        ) = context.startWith<PostListActivity>("missionViewItem" to missionViewItem)
    }
}

