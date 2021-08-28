package org.positive.daymotion.data.repository

import io.reactivex.rxjava3.core.Single
import org.positive.daymotion.data.api.MissionApi
import org.positive.daymotion.domain.DayMission
import org.positive.daymotion.domain.Mission
import javax.inject.Inject

class MissionRepositoryImpl @Inject constructor(
    private val missionApi: MissionApi
) : MissionRepository {
    override fun loadTodayMissions(): List<Mission> {
        return listOf(
            Mission(
                "categoryName",
                "도시의 밤 향기를 닮은 너!",
                "2021-08-17",
                "1",
                "image",
                "question"
            ),
            Mission(
                "categoryName",
                "아아? 라떼? 커피를 추천해줘!",
                "2021-08-17",
                "1",
                "image",
                "question"
            ),
            Mission(
                "categoryName",
                "털복숭이 친구들을 소개해줘요!",
                "2021-08-17",
                "1",
                "image",
                "question"
            )
        )
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