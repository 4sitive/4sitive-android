package org.positive.daymotion.data.api

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.positive.daymotion.data.model.GetFeedResponse
import org.positive.daymotion.data.model.GetFeedsResponse
import org.positive.daymotion.data.model.PostFeedRequest
import org.positive.daymotion.data.model.PutEmojiRequest
import retrofit2.http.*

interface FeedApi {

    @GET("/feeds/{id}")
    fun getFeed(
        @Path("id") id: String
    ): Single<GetFeedResponse>

    @GET("/feeds")
    fun getFeeds(
        @Query("userId") userId: String? = null,
        @Query("missionId") missionId: String? = null,
        @Query("categoryId") categoryId: String? = null
    ): Single<GetFeedsResponse>

    @POST("/feeds")
    fun postFeeds(
        @Body request: PostFeedRequest
    ): Completable

    @PUT("/feeds/{id}/emoji")
    fun putEmoji(
        @Path("id") id: String,
        @Body request: PutEmojiRequest
    ): Completable
}