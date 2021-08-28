package org.positive.daymotion.data.api

import io.reactivex.rxjava3.core.Single
import org.positive.daymotion.data.model.GetLastMissionsResponse
import retrofit2.http.GET

interface MissionApi {

    @GET("/missions")
    fun getLastMissions(): Single<GetLastMissionsResponse>
}