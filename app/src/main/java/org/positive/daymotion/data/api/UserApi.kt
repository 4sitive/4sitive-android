package org.positive.daymotion.data.api

import io.reactivex.rxjava3.core.Single
import org.positive.daymotion.data.model.GetUserResponse
import org.positive.daymotion.data.model.PutUserRequest
import org.positive.daymotion.data.model.PutUserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface UserApi {

    @GET("/users")
    fun getUser(): Single<GetUserResponse>

    @PUT("/users")
    fun putUser(@Body request: PutUserRequest): Single<PutUserResponse>
}