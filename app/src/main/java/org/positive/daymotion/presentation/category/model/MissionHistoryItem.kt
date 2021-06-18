package org.positive.daymotion.presentation.category.model

data class MissionHistoryItem(
    val date: String,
    val missions: List<MissionHistoryInnerItem>
) {

    val isMissionEmpty = missions.isEmpty()

    val firstMission = missions.getOrNull(0)

    val secondMission = missions.getOrNull(1)

    val thirdMission = missions.getOrNull(2)
}