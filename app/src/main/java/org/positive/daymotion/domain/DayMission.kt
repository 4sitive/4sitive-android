package org.positive.daymotion.domain

data class DayMission(
    val date: String,
    val firstMission: Mission,
    val secondMission: Mission,
    val thirdMission: Mission
)