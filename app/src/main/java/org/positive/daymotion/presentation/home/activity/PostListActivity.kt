package org.positive.daymotion.presentation.home.activity

import android.content.Context
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_VERTICAL
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityPostListBinding
import org.positive.daymotion.presentation.common.base.BaseActivity
import org.positive.daymotion.presentation.common.base.viewModelOf
import org.positive.daymotion.presentation.common.bundle
import org.positive.daymotion.presentation.common.extension.startWith
import org.positive.daymotion.presentation.home.adapter.FeedPagerAdapter
import org.positive.daymotion.presentation.home.fragment.HeaderFeedFragment
import org.positive.daymotion.presentation.home.model.MissionViewItem
import org.positive.daymotion.presentation.home.viewmodel.PostListViewModel

@AndroidEntryPoint
class PostListActivity :
    BaseActivity<ActivityPostListBinding>(R.layout.activity_post_list),
    HeaderFeedFragment.EventListener {

    private val viewModel by viewModelOf<PostListViewModel>()
    private val missionViewItem by bundle<MissionViewItem>()
    private val feedPagerAdapter by lazy { FeedPagerAdapter(this, missionViewItem) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        setupObservers()

        viewModel.loadFeedList(missionViewItem.id)
    }

    override fun onCollapsingStateChanged(isCollapsed: Boolean) {
        binding.feedViewPager.isUserInputEnabled = isCollapsed
    }

    private fun setupViews() {
        with(binding) {
            feedViewPager.orientation = ORIENTATION_VERTICAL
        }
    }

    private fun setupObservers() {
        viewModel.feeds.observeNonNull {
            binding.feedViewPager.adapter = feedPagerAdapter
            feedPagerAdapter.replace(it)
        }
    }


    companion object {
        fun start(
            context: Context,
            missionViewItem: MissionViewItem
        ) = context.startWith<PostListActivity>("missionViewItem" to missionViewItem)
    }
}

