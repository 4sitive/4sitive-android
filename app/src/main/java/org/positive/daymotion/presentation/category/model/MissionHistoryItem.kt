package org.positive.daymotion.presentation.category.model

data class MissionHistoryItem(
    val date: String,
    val missions: List<MissionHistoryInnerItem>
)