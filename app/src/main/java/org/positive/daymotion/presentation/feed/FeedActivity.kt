package org.positive.daymotion.presentation.feed

import android.content.Context
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityFeedBinding
import org.positive.daymotion.presentation.common.extension.startWith
import org.positive.daymotion.presentation.common.base.BaseActivity
import org.positive.daymotion.presentation.common.base.viewModelOf

@AndroidEntryPoint
class FeedActivity : BaseActivity<ActivityFeedBinding>(R.layout.activity_feed) {

    private val viewModel by viewModelOf<FeedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
    }

    companion object {
        fun start(context: Context) = context.startWith<FeedActivity>()
    }
}