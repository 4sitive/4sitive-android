package org.positive.daymotion.data.repository

import io.reactivex.rxjava3.core.Single
import org.positive.daymotion.data.api.MissionApi
import org.positive.daymotion.domain.DayMission
import org.positive.daymotion.domain.Mission
import javax.inject.Inject

class MissionRepositoryImpl @Inject constructor(
    private val missionApi: MissionApi
) : MissionRepository {

    override fun loadTodayMissions(): Single<List<Mission>> {
        return missionApi.getTodayMissions().map {
            it.content?.map { mission ->
                Mission(
                    mission.categoryName,
                    mission.content.orEmpty(),
                    mission.date,
                    mission.id,
                    mission.image,
                    mission.question.orEmpty()
                )
            }.orEmpty()
        }
    }

    override fun loadLastMissions(): Single<List<DayMission>> {
        return missionApi.getLastMissions().map { response ->
            val grouping = response.content.groupBy { it.date }
            grouping.map {
                DayMission(
                    it.key,
                    it.value.map { v ->
                        Mission(
                            v.categoryName.orEmpty(),
                            v.content.orEmpty(),
                            v.date,
                            v.id,
                            v.image,
                            v.question.orEmpty()
                        )
                    }
                )
            }
        }
    }
}