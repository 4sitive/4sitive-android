package org.positive.daymotion.data.repository

import org.positive.daymotion.domain.Mission

interface MissionRepository {
    fun loadTodayMissions(): List<Mission>
}