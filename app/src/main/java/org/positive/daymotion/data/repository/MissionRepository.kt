package org.positive.daymotion.data.repository

import io.reactivex.rxjava3.core.Single
import org.positive.daymotion.domain.DayMission
import org.positive.daymotion.domain.Mission

interface MissionRepository {

    fun loadTodayMissions(): Single<List<Mission>>

    fun loadLastMissions(): Single<List<DayMission>>
}