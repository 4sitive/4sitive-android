package org.positive.daymotion.data.repository

import io.reactivex.rxjava3.core.Single
import org.positive.daymotion.data.api.FeedApi
import org.positive.daymotion.domain.Feed
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val feedApi: FeedApi
) : FeedRepository {

    override fun getFeedWithUserId(userId: String): Single<List<Feed>> {
        return feedApi.getFeeds(userId = userId).map { response ->
            response.content.map {
                Feed(
                    it.id,
                    it.image,
                    it.categoryName,
                    it.missionQuestion,
                    it.user.id,
                    it.user.image,
                    it.user.name ?: it.user.username
                )
            }
        }
    }

    override fun getFeedWithCategoryId(categoryId: String): Single<List<Feed>> {
        return feedApi.getFeeds(categoryId = categoryId).map { response ->
            response.content.map {
                Feed(
                    it.id,
                    it.image,
                    it.categoryName,
                    it.missionQuestion,
                    it.user.id,
                    it.user.image,
                    it.user.name ?: it.user.username
                )
            }
        }
    }
}