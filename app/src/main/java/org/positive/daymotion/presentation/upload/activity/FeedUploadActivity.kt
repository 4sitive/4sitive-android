package org.positive.daymotion.presentation.upload.activity

import android.content.Context
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityFeedUploadBinding
import org.positive.daymotion.presentation.common.base.BaseActivity
import org.positive.daymotion.presentation.common.base.viewModelOf
import org.positive.daymotion.presentation.common.extension.startWith
import org.positive.daymotion.presentation.upload.FeedUploadViewModel

@AndroidEntryPoint
class FeedUploadActivity :
    BaseActivity<ActivityFeedUploadBinding>(R.layout.activity_feed_upload) {

    private val viewModel by viewModelOf<FeedUploadViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
    }

    companion object {
        fun start(context: Context) = context.startWith<FeedUploadActivity>()
    }
}