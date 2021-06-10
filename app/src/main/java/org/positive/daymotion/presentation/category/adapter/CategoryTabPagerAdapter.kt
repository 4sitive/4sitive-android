package org.positive.daymotion.presentation.category.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.positive.daymotion.presentation.category.page.CategoryPageFragment
import org.positive.daymotion.presentation.category.page.MissionHistoryPageFragment

class CategoryTabPagerAdapter(parentFragment: Fragment) : FragmentStateAdapter(parentFragment) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int) = when (position) {
        0 -> MissionHistoryPageFragment()
        1 -> CategoryPageFragment()
        else -> throw IllegalStateException("Exceed max page number")
    }
}