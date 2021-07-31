package org.positive.daymotion.presentation.home.adapter

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.positive.daymotion.presentation.home.fragment.FeedFragment
import org.positive.daymotion.presentation.home.fragment.HeaderFeedFragment
import org.positive.daymotion.presentation.home.model.MissionViewItem

class FeedPagerAdapter(
    parentActivity: FragmentActivity,
    private val missionViewItem: MissionViewItem
) : FragmentStateAdapter(parentActivity) {

    override fun getItemCount(): Int = 6

    override fun createFragment(position: Int) = if (position == 0) {
        HeaderFeedFragment.newInstance(missionViewItem, null)
    } else {
        FeedFragment()
    }
}