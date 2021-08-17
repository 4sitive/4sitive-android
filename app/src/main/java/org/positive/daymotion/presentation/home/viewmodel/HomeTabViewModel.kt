package org.positive.daymotion.presentation.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.data.repository.MissionRepository
import org.positive.daymotion.presentation.common.base.BaseViewModel
import org.positive.daymotion.presentation.home.model.MissionViewItem
import javax.inject.Inject

@HiltViewModel
class HomeTabViewModel @Inject constructor(
    private val missionRepository: MissionRepository
) : BaseViewModel() {

    private val _todayMissions = MutableLiveData<List<MissionViewItem>>()
    val todayMissions: LiveData<List<MissionViewItem>> get() = _todayMissions

    fun loadTodayMissions() {
        _todayMissions.value = missionRepository.loadTodayMissions()
            .mapIndexed { i, mission -> MissionViewItem.of(mission, i) }
    }
}