package org.positive.daymotion.presentation.category.fragment

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.FragmentMissionHistoryPageBinding
import org.positive.daymotion.presentation.base.BaseFragment
import org.positive.daymotion.presentation.base.util.viewModelOf
import org.positive.daymotion.presentation.category.adapter.MissionHistoryAdapter
import org.positive.daymotion.presentation.category.viewmodel.MissionHistoryPageViewModel

@AndroidEntryPoint
class MissionHistoryPageFragment :
    BaseFragment<FragmentMissionHistoryPageBinding>(R.layout.fragment_mission_history_page) {

    private val viewModel by viewModelOf<MissionHistoryPageViewModel>()
    private val adapter by lazy { MissionHistoryAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        setupViews()
        setupObservers()

        viewModel.loadMissionHistories()
    }

    private fun setupViews() {
        binding.missionsHistoryRecyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.missionHistories.observeNonNull {
            adapter.replaceAll(it)
        }
    }
}