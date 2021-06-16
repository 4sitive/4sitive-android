package org.positive.daymotion.presentation.category.model

data class MissionHistoryItem(
    val date: String,
    val missions: List<MissionHistoryInnerItem>,
    val savedPosition: Int = 0,
    val savedPositionOffset: Int = 0
) {

    val isMissionEmpty = missions.isEmpty()

    val firstMission = missions.getOrNull(0)

    val secondMission = missions.getOrNull(0)

    val thirdMission = missions.getOrNull(0)
}