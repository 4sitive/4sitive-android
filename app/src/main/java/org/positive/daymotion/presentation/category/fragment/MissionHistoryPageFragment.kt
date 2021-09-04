package org.positive.daymotion.presentation.category.fragment

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.FragmentMissionHistoryPageBinding
import org.positive.daymotion.presentation.category.activity.CategoryDetailActivity
import org.positive.daymotion.presentation.category.adapter.MissionHistoryAdapter
import org.positive.daymotion.presentation.category.model.DetailQueryType
import org.positive.daymotion.presentation.category.viewmodel.MissionHistoryPageViewModel
import org.positive.daymotion.presentation.common.ScrollableFragment
import org.positive.daymotion.presentation.common.base.BaseFragment
import org.positive.daymotion.presentation.common.base.viewModelOf

@AndroidEntryPoint
class MissionHistoryPageFragment :
    BaseFragment<FragmentMissionHistoryPageBinding>(R.layout.fragment_mission_history_page),
    ScrollableFragment {

    private val viewModel by viewModelOf<MissionHistoryPageViewModel>()
    private val handler by lazy { Handler() }
    private val missionHistoryAdapter by lazy { MissionHistoryAdapter(handler) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupObservers()

        viewModel.loadMissionHistories()
    }

    override fun scrollToTop() {
        binding.missionsHistoryRecyclerView.smoothScrollToPosition(0)
    }

    private fun setupViews() {
        binding.missionsHistoryRecyclerView.adapter = missionHistoryAdapter
    }

    private fun setupObservers() {
        viewModel.missionHistories.observeNonNull {
            missionHistoryAdapter.replaceAll(it)
        }
    }

    inner class Handler {
        fun goToCategoryDetail(title: String, id: String, detailQueryType: DetailQueryType) =
            CategoryDetailActivity.start(requireContext(), title, id, detailQueryType)
    }
}