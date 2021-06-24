package org.positive.daymotion.presentation.category.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.positive.daymotion.presentation.category.fragment.CategoryBrowserPageFragment
import org.positive.daymotion.presentation.category.fragment.MissionHistoryPageFragment

class CategoryTabPagerAdapter(parentFragment: Fragment) : FragmentStateAdapter(parentFragment) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> MissionHistoryPageFragment()
        1 -> CategoryBrowserPageFragment()
        else -> throw IllegalStateException("Exceed max page number")
    }
}