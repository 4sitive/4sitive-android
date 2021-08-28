package org.positive.daymotion.presentation.category.model

import org.positive.daymotion.domain.DayMission

data class MissionHistoryItem(
    val date: String,
    val missions: List<MissionHistoryInnerItem>
) {

    val isMissionEmpty = missions.isEmpty()

    val firstMission = missions.getOrNull(0)

    val secondMission = missions.getOrNull(1)

    val thirdMission = missions.getOrNull(2)

    companion object {
        fun of(dayMission: DayMission): MissionHistoryItem {
            return MissionHistoryItem(
                dayMission.date,
                listOf(
                    MissionHistoryInnerItem(
                        dayMission.firstMission.question,
                        dayMission.firstMission.image
                    ),
                    MissionHistoryInnerItem(
                        dayMission.secondMission.question,
                        dayMission.secondMission.image
                    ),
                    MissionHistoryInnerItem(
                        dayMission.thirdMission.question,
                        dayMission.thirdMission.image
                    ),
                )
            )
        }
    }
}