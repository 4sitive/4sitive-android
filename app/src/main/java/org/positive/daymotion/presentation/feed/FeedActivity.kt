package org.positive.daymotion.presentation.feed

import android.app.Activity
import android.content.Context
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityFeedBinding
import org.positive.daymotion.presentation.common.base.BaseActivity
import org.positive.daymotion.presentation.common.base.viewModelOf
import org.positive.daymotion.presentation.common.bundle
import org.positive.daymotion.presentation.common.extension.registerActivityResult
import org.positive.daymotion.presentation.common.extension.startWith
import org.positive.daymotion.presentation.home.activity.AddEmojiActivity
import org.positive.daymotion.presentation.home.adapter.EmojiItemAdapter
import org.positive.daymotion.presentation.home.model.EmojiItem

@AndroidEntryPoint
class FeedActivity : BaseActivity<ActivityFeedBinding>(R.layout.activity_feed) {

    private val viewModel by viewModelOf<FeedViewModel>()
    private val feedId by bundle<String>()

    private val emojiItemAdapter = EmojiItemAdapter()

    private val launcher = registerActivityResult {
        val data = it.data
        val resultCode = it.resultCode
        if (data != null && resultCode == Activity.RESULT_OK) {
            data.getParcelableArrayListExtra<EmojiItem>("updatedEmojis")?.let { emojis ->
                emojiItemAdapter.replaceAll(emojis)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.handler = Handler()
        binding.viewModel = viewModel

        binding.emojiRecyclerView.adapter = emojiItemAdapter
        viewModel.feed.observeNonNull {
            emojiItemAdapter.replaceAll(it.emojis)
        }

        viewModel.getFeed(feedId)

    }

    inner class Handler {
        fun finish() = this@FeedActivity.finish()

        fun addEmoji() {
            AddEmojiActivity.startForResult(
                this@FeedActivity,
                launcher,
                feedId,
                ArrayList(emojiItemAdapter.getItems())
            )
        }
    }

    companion object {
        fun start(
            context: Context,
            feedId: String
        ) = context.startWith<FeedActivity>("feedId" to feedId)
    }
}
