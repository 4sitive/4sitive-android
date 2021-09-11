package org.positive.daymotion.domain

data class DayMission(
    val isYesterday: Boolean,
    val date: String,
    val missions: List<Mission>
)