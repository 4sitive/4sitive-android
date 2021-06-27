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
import org.positive.daymotion.presentation.feed.adapter.EmojiAdapter

@AndroidEntryPoint
class FeedActivity : BaseActivity<ActivityFeedBinding>(R.layout.activity_feed) {

    private val viewModel by viewModelOf<FeedViewModel>()
    private val emojiAdapter by lazy { EmojiAdapter() }
    private val title by bundle<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.title = title

        setupViews()
        setupObservers()

        viewModel.loadFeedInformation()
    }

    private fun setupViews() {
        binding.emojiRecyclerView.adapter = emojiAdapter
    }

    private fun setupObservers() {
        viewModel.feedInformation.observeNonNull {
            emojiAdapter.replaceAll(it.emojis)
        }
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