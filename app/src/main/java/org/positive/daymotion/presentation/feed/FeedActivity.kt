package org.positive.daymotion.presentation.feed

import android.content.Context
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityFeedBinding
import org.positive.daymotion.presentation.common.base.BaseActivity
import org.positive.daymotion.presentation.common.base.viewModelOf
import org.positive.daymotion.presentation.common.bundle
import org.positive.daymotion.presentation.common.extension.startWith

@AndroidEntryPoint
class FeedActivity : BaseActivity<ActivityFeedBinding>(R.layout.activity_feed) {

    private val viewModel by viewModelOf<FeedViewModel>()

    private val title by bundle<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.title = title
    }

    inner class Handler {
        fun finish() = this@FeedActivity.finish()
    }

    companion object {
        fun start(
            context: Context,
            title: String
        ) = context.startWith<FeedActivity>("title" to title)
    }
}