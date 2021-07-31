package org.positive.daymotion.presentation.home.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.google.android.material.appbar.AppBarLayout
import org.positive.daymotion.R
import org.positive.daymotion.databinding.FragmentHeaderFeedBinding
import org.positive.daymotion.presentation.common.base.BaseFragment
import org.positive.daymotion.presentation.common.bundle
import org.positive.daymotion.presentation.home.model.FeedViewItem
import org.positive.daymotion.presentation.home.model.MissionViewItem
import org.positive.daymotion.presentation.upload.activity.FeedUploadActivity

class HeaderFeedFragment : BaseFragment<FragmentHeaderFeedBinding>(R.layout.fragment_header_feed) {

    private val missionViewItem by bundle<MissionViewItem>()
    private val feedViewItem by bundle<FeedViewItem?>()

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
        binding.appBarLayout.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, offset ->
                val total = appBarLayout.totalScrollRange
                val percentage = (total + offset) / total.toFloat()
                binding.imageViewContainer.alpha = percentage
                binding.homeMissionCard.alpha = percentage
                binding.toolbar.alpha = 1 - percentage
                eventListener?.onCollapsingStateChanged(percentage == 0f)
            }
        )
    }

    interface EventListener {
        fun onCollapsingStateChanged(isCollapsed: Boolean)
    }

    inner class Handler {
        fun finish() = requireActivity().finish()
        fun startFeedUploadActivity() = FeedUploadActivity.start(requireContext())
    }

    companion object {
        fun newInstance(
            missionViewItem: MissionViewItem,
            feedViewItem: FeedViewItem?
        ) = HeaderFeedFragment().apply {
            arguments = bundleOf(
                "missionViewItem" to missionViewItem,
                "feedViewItem" to feedViewItem
            )
        }
    }
}