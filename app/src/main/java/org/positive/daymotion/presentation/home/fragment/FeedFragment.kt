package org.positive.daymotion.presentation.home.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import org.positive.daymotion.R
import org.positive.daymotion.databinding.FragmentFeedBinding
import org.positive.daymotion.presentation.common.base.BaseFragment
import org.positive.daymotion.presentation.common.bundle
import org.positive.daymotion.presentation.home.model.FeedViewItem
import org.positive.daymotion.presentation.home.model.MissionViewItem
import org.positive.daymotion.presentation.upload.activity.FeedUploadActivity

class FeedFragment : BaseFragment<FragmentFeedBinding>(R.layout.fragment_feed) {

    private val missionViewItem by bundle<MissionViewItem>()
    private val feedViewItem by bundle<FeedViewItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.handler = Handler()
        binding.missionViewItem = missionViewItem
        binding.feedViewItem = feedViewItem
    }

    inner class Handler {
        fun finish() = requireActivity().finish()
        fun startFeedUploadActivity() = FeedUploadActivity.start(requireContext())
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