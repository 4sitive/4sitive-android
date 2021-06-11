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
            add(MissionHistoryItem(
                date = "Yesterday",
                missions = buildList {
                    add(MissionHistoryInnerItem("mission title1"))
                    add(MissionHistoryInnerItem("mission title2"))
                    add(MissionHistoryInnerItem("mission title3"))
                    add(MissionHistoryInnerItem("mission title1"))
                    add(MissionHistoryInnerItem("mission title2"))
                    add(MissionHistoryInnerItem("mission title3"))
                    add(MissionHistoryInnerItem("mission title1"))
                    add(MissionHistoryInnerItem("mission title2"))
                    add(MissionHistoryInnerItem("mission title3"))
                    add(MissionHistoryInnerItem("mission title1"))
                    add(MissionHistoryInnerItem("mission title2"))
                    add(MissionHistoryInnerItem("mission title3"))
                }
            ))

            add(MissionHistoryItem(
                date = "06.10",
                missions = buildList {
                    add(MissionHistoryInnerItem("mission title4"))
                    add(MissionHistoryInnerItem("mission title5"))
                    add(MissionHistoryInnerItem("mission title6"))
                }
            ))

            add(MissionHistoryItem(
                date = "06.09",
                missions = buildList {
                    add(MissionHistoryInnerItem("mission title7"))
                    add(MissionHistoryInnerItem("mission title8"))
                    add(MissionHistoryInnerItem("mission title9"))
                }
            ))

            add(MissionHistoryItem(
                date = "06.08",
                missions = buildList {
                    add(MissionHistoryInnerItem("mission title10"))
                    add(MissionHistoryInnerItem("mission title11"))
                    add(MissionHistoryInnerItem("mission title12"))
                }
            ))

            add(MissionHistoryItem(
                date = "06.07",
                missions = buildList {
                    add(MissionHistoryInnerItem("mission title13"))
                    add(MissionHistoryInnerItem("mission title14"))
                    add(MissionHistoryInnerItem("mission title15"))
                }
            ))

            add(MissionHistoryItem(
                date = "06.06",
                missions = buildList {
                    add(MissionHistoryInnerItem("mission title16"))
                    add(MissionHistoryInnerItem("mission title17"))
                    add(MissionHistoryInnerItem("mission title18"))
                }
            ))

            add(MissionHistoryItem(
                date = "06.05",
                missions = buildList {
                    add(MissionHistoryInnerItem("mission title19"))
                    add(MissionHistoryInnerItem("mission title20"))
                    add(MissionHistoryInnerItem("mission title21"))
                }
            ))
        }

        _missionHistories.value = items
    }
}