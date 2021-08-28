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
import org.positive.daymotion.presentation.home.adapter.TodayMissionAdapter
import org.positive.daymotion.presentation.home.model.MissionViewItem
import org.positive.daymotion.presentation.home.viewmodel.HomeTabViewModel


@AndroidEntryPoint
class HomeTabFragment : BaseFragment<FragmentHomeTabBinding>(R.layout.fragment_home_tab),
    ScrollableFragment {

    private val viewModel by viewModelOf<HomeTabViewModel>()
    private val handler = Handler()
    private val adapter = TodayMissionAdapter(handler)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        setupViews()
        setupObservers()

        viewModel.loadTodayMissions()
        viewModel.loadUserProfile()
    }

    override fun scrollToTop() {
        binding.scrollView.smoothScrollTo(0, binding.profileLayout.top)
    }

    private fun setupViews() {
        with(binding) {
            todayRecyclerView.adapter = adapter
        }
    }

    private fun setupObservers() {
        with(viewModel) {
            todayMissions.observeNonNull {
                adapter.replaceAll(it)
            }
        }
    }

    inner class Handler {
        fun sendSubject(missionViewItem: MissionViewItem) =
            PostListActivity.start(requireContext(), missionViewItem)
    }
}
