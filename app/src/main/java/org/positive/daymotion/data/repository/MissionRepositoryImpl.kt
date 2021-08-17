package org.positive.daymotion.data.repository

import org.positive.daymotion.domain.Mission
import javax.inject.Inject

class MissionRepositoryImpl @Inject constructor() : MissionRepository {
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
}