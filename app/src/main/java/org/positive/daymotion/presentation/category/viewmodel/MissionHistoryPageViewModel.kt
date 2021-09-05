package org.positive.daymotion.presentation.category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.data.repository.MissionRepository
import org.positive.daymotion.presentation.category.model.MissionHistoryItem
import org.positive.daymotion.presentation.common.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MissionHistoryPageViewModel @Inject constructor(
    private val missionRepository: MissionRepository
) : BaseViewModel() {

    private val _missionHistories = MutableLiveData<List<MissionHistoryItem>>()
    val missionHistories: LiveData<List<MissionHistoryItem>> get() = _missionHistories

    fun loadMissionHistories() {
        missionRepository.loadLastMissions()
            .apiLoadingCompose()
            .autoDispose {
                success { result ->
                    _missionHistories.value = result.map { MissionHistoryItem.of(it) }
                }
                error {
                    showErrorMessage(it.message.orEmpty())
                }
            }
    }
}