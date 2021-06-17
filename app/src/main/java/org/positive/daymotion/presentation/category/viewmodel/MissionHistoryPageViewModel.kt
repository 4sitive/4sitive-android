package org.positive.daymotion.presentation.category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.presentation.base.BaseViewModel
import org.positive.daymotion.presentation.category.model.MissionHistoryInnerItem
import org.positive.daymotion.presentation.category.model.MissionHistoryItem
import javax.inject.Inject

@HiltViewModel
class MissionHistoryPageViewModel @Inject constructor() : BaseViewModel() {

    private val _missionHistories = MutableLiveData<List<MissionHistoryItem>>()
    val missionHistories: LiveData<List<MissionHistoryItem>> get() = _missionHistories

    fun loadMissionHistories() {
        val items = buildList {
            add(
                MissionHistoryItem(
                    date = "Yesterday",
                    missions = emptyList()
                )
            )

            add(MissionHistoryItem(
                date = "06.05",
                missions = buildList {
                    add(MissionHistoryInnerItem("mission title4", "imageUrl"))
                }
            ))

            add(MissionHistoryItem(
                date = "06.04",
                missions = buildList {
                    add(MissionHistoryInnerItem("mission title7", "imageUrl"))
                    add(MissionHistoryInnerItem("mission title8", "imageUrl"))
                }
            ))

            add(MissionHistoryItem(
                date = "06.03",
                missions = buildList {
                    add(MissionHistoryInnerItem("mission title10", "imageUrl"))
                    add(MissionHistoryInnerItem("mission title11", "imageUrl"))
                    add(MissionHistoryInnerItem("mission title12", "imageUrl"))
                }
            ))

            add(MissionHistoryItem(
                date = "06.02",
                missions = buildList {
                    add(MissionHistoryInnerItem("mission title13", "imageUrl"))
                    add(MissionHistoryInnerItem("mission title14", "imageUrl"))
                    add(MissionHistoryInnerItem("mission title15", "imageUrl"))
                }
            ))

            add(MissionHistoryItem(
                date = "06.01",
                missions = buildList {
                    add(MissionHistoryInnerItem("mission title16", "imageUrl"))
                    add(MissionHistoryInnerItem("mission title17", "imageUrl"))
                    add(MissionHistoryInnerItem("mission title18", "imageUrl"))
                }
            ))
        }

        _missionHistories.value = items
    }
}