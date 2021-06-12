package org.positive.daymotion.presentation.category.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.FragmentCategoryTabBinding
import org.positive.daymotion.databinding.WidgetCategoryTabIndicatorBinding
import org.positive.daymotion.presentation.base.BaseFragment
import org.positive.daymotion.presentation.base.util.viewModelOf
import org.positive.daymotion.presentation.category.viewmodel.CategoryTabViewModel
import org.positive.daymotion.presentation.category.adapter.CategoryTabPagerAdapter
import org.positive.daymotion.presentation.root.model.RootTabFragment

@AndroidEntryPoint
class CategoryTabFragment :
    BaseFragment<FragmentCategoryTabBinding>(R.layout.fragment_category_tab), RootTabFragment {

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
            // TODO(yh): replace to scroll logic
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        setupViews()
    }

    private fun setupViews() {
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        viewPager.adapter = pagerAdapter
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

    override fun scrollToTop() {
        // TODO(yh): replace to scroll logic
        Toast.makeText(requireContext(), "Category", Toast.LENGTH_SHORT).show()
    }
}