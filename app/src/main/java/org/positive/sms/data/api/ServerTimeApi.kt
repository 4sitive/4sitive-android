package org.positive.sms.data.api

import io.reactivex.rxjava3.core.Single
import org.positive.sms.data.model.ServerTimeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ServerTimeApi {

    @GET(value = "api/timezone/{timezone}")
    fun loadServerTime(@Path("timezone") timeZone: String): Single<ServerTimeResponse>
}