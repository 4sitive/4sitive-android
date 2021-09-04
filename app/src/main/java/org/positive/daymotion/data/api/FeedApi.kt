package org.positive.daymotion.data.api

import io.reactivex.rxjava3.core.Single
import org.positive.daymotion.data.model.GetFeedsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FeedApi {

    @GET("/feeds")
    fun getFeeds(
        @Query("userId") userId: String? = null,
        @Query("categoryId") categoryId: String? = null
    ): Single<GetFeedsResponse>
}