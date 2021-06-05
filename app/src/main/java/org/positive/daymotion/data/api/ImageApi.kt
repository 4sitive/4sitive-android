package org.positive.daymotion.data.api

import io.reactivex.rxjava3.core.Single
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface ImageApi {

    @PUT("/{fileName}")
    fun imageUpload(
        @Path("fileName") fileName: String,
        @Body requestBody: RequestBody
    ): Single<Response<Unit>>
}