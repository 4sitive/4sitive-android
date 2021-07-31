package org.positive.daymotion.presentation.home.adapter

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.positive.daymotion.presentation.home.fragment.FeedFragment
import org.positive.daymotion.presentation.home.fragment.HeaderFeedFragment
import org.positive.daymotion.presentation.home.model.FeedViewItem
import org.positive.daymotion.presentation.home.model.MissionViewItem

class FeedPagerAdapter(
    parentActivity: FragmentActivity,
    private val missionViewItem: MissionViewItem
) : FragmentStateAdapter(parentActivity) {

    private val items = mutableListOf<FeedViewItem>()

    fun replace(items: List<FeedViewItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount() = if (items.isEmpty()) 1 else items.size


    override fun createFragment(position: Int) = if (position == 0) {
        HeaderFeedFragment.newInstance(missionViewItem, items.getOrNull(0))
    } else {
        FeedFragment.newInstance(missionViewItem, items[position])
    }
}