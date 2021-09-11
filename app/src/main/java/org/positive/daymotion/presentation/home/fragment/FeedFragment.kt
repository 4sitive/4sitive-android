package org.positive.daymotion.presentation.home.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import org.positive.daymotion.R
import org.positive.daymotion.databinding.FragmentFeedBinding
import org.positive.daymotion.presentation.common.base.BaseFragment
import org.positive.daymotion.presentation.common.bundle
import org.positive.daymotion.presentation.common.extension.registerActivityResult
import org.positive.daymotion.presentation.home.activity.AddEmojiActivity
import org.positive.daymotion.presentation.home.adapter.EmojiItemAdapter
import org.positive.daymotion.presentation.home.model.EmojiItem
import org.positive.daymotion.presentation.home.model.FeedViewItem
import org.positive.daymotion.presentation.home.model.MissionViewItem
import org.positive.daymotion.presentation.upload.activity.FeedUploadActivity

class FeedFragment : BaseFragment<FragmentFeedBinding>(R.layout.fragment_feed) {

    private val missionViewItem by bundle<MissionViewItem>()
    private val feedViewItem by bundle<FeedViewItem>()
    private val emojiItemAdapter by lazy { EmojiItemAdapter() }

    private val launcher = registerActivityResult {
        val data = it.data
        val resultCode = it.resultCode
        if (data != null && resultCode == Activity.RESULT_OK) {
            data.getParcelableArrayListExtra<EmojiItem>("updatedEmojis")?.let { emojis ->
                emojiItemAdapter.replaceAll(emojis)
            }
        }
    }

    private val uploadLauncher = registerActivityResult {
        if (it.resultCode == Activity.RESULT_OK) {
            eventListener?.reload()
        }
    }

    private var eventListener: EventListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is EventListener) {
            eventListener = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.handler = Handler()
        binding.missionViewItem = missionViewItem
        binding.feedViewItem = feedViewItem

        setupViews()
    }

    private fun setupViews() {
        binding.emojiRecyclerView.adapter = emojiItemAdapter
        emojiItemAdapter.replaceAll(feedViewItem.emojis)
    }

    interface EventListener {
        fun reload()
    }

    inner class Handler {
        fun finish() = requireActivity().finish()

        fun startFeedUploadActivity() = FeedUploadActivity.startForResult(
            requireContext(),
            uploadLauncher,
            missionViewItem.id
        )

        fun addEmoji() {
            AddEmojiActivity.startForResult(
                requireContext(),
                launcher,
                feedViewItem.id,
                ArrayList(emojiItemAdapter.getItems())
            )
        }
    }

    companion object {
        fun newInstance(
            missionViewItem: MissionViewItem,
            feedViewItem: FeedViewItem
        ) = FeedFragment().apply {
            arguments = bundleOf(
                "missionViewItem" to missionViewItem,
                "feedViewItem" to feedViewItem
            )
        }
    }
}