package org.positive.daymotion.presentation.category.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.FragmentCategoryTabBinding
import org.positive.daymotion.databinding.WidgetCategoryTabIndicatorBinding
import org.positive.daymotion.presentation.common.base.BaseFragment
import org.positive.daymotion.presentation.category.adapter.CategoryTabPagerAdapter
import org.positive.daymotion.presentation.category.viewmodel.CategoryTabViewModel
import org.positive.daymotion.presentation.common.ScrollableFragment
import org.positive.daymotion.presentation.common.base.viewModelOf

@AndroidEntryPoint
class CategoryTabFragment :
    BaseFragment<FragmentCategoryTabBinding>(R.layout.fragment_category_tab), ScrollableFragment {

    private val viewModel by viewModelOf<CategoryTabViewModel>()
    private val pagerAdapter by lazy { CategoryTabPagerAdapter(this) }

    private val onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            findTabBinding(tab)?.apply { isSelected = true }
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {
            findTabBinding(tab)?.apply { isSelected = false }
        }

        override fun onTabReselected(tab: TabLayout.Tab) {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        setupViews()
    }

    override fun scrollToTop() {
        findCurrentFragment().let {
            if (it is ScrollableFragment) {
                it.scrollToTop()
            }
        }
    }

    private fun setupViews() {
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        viewPager.apply {
            adapter = pagerAdapter
            getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            initTab(tab, position)
        }.attach()

        tabLayout.addOnTabSelectedListener(onTabSelectedListener)
    }

    private fun initTab(tab: TabLayout.Tab, position: Int) {
        val layoutInflater = LayoutInflater.from(requireContext())
        val viewBinding = WidgetCategoryTabIndicatorBinding.inflate(layoutInflater)

        viewBinding.title = if (position == 0) "미션 히스토리" else "카테고리"
        viewBinding.isSelected = position == 0
        tab.customView = viewBinding.root
    }

    private fun findTabBinding(tab: TabLayout.Tab): WidgetCategoryTabIndicatorBinding? {
        return tab.customView?.let { DataBindingUtil.findBinding(it) }
    }

    private fun findCurrentFragment(): Fragment? {
        val currentPosition = binding.viewPager.currentItem
        return childFragmentManager.findFragmentByTag("f$currentPosition")
    }
}