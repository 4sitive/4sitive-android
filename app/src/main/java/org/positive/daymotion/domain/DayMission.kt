package org.positive.daymotion.domain

data class DayMission(
    val date: String,
    val missions: List<Mission>
)