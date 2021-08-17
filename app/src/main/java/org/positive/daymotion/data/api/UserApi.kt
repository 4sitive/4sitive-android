package org.positive.daymotion.data.api

import io.reactivex.rxjava3.core.Single
import org.positive.daymotion.data.model.GetUserResponse
import retrofit2.http.GET

interface UserApi {

    @GET("/users")
    fun getUser(): Single<GetUserResponse>
}