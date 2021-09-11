package org.positive.daymotion.presentation.category.model

import org.positive.daymotion.domain.DayMission
import org.positive.daymotion.domain.Mission

data class MissionHistoryItem(
    val isYesterday: Boolean,
    val date: String,
    val missions: List<Mission>
) {

    val formattedDate = if (isYesterday) "Yesterday" else date

    val isMissionEmpty = missions.isEmpty()

    val firstMission = missions.getOrNull(0)

    val secondMission = missions.getOrNull(1)

    val thirdMission = missions.getOrNull(2)

    companion object {
        fun of(dayMission: DayMission): MissionHistoryItem {
            return MissionHistoryItem(
                dayMission.isYesterday,
                dayMission.date,
                dayMission.missions
            )
        }
    }
}