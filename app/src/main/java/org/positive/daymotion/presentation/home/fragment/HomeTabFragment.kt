package org.positive.daymotion.presentation.home.fragment

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.FragmentHomeTabBinding
import org.positive.daymotion.presentation.common.ScrollableFragment
import org.positive.daymotion.presentation.common.base.BaseFragment
import org.positive.daymotion.presentation.common.base.viewModelOf
import org.positive.daymotion.presentation.home.activity.PostListActivity
import org.positive.daymotion.presentation.home.model.MissionViewItem
import org.positive.daymotion.presentation.home.viewmodel.HomeTabViewModel


@AndroidEntryPoint
class HomeTabFragment : BaseFragment<FragmentHomeTabBinding>(R.layout.fragment_home_tab),
    ScrollableFragment {

    private val viewModel by viewModelOf<HomeTabViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.handler = Handler()
        binding.viewModel = viewModel

        viewModel.loadTodayMissions()
    }

    override fun scrollToTop() {
        binding.scrollView.smoothScrollTo(0, binding.profileLayout.top)
    }

    inner class Handler {
        fun sendSubject(missionViewItem: MissionViewItem) =
            PostListActivity.start(requireContext(), missionViewItem)
    }
}
