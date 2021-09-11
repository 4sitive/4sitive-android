package org.positive.daymotion.data.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.positive.daymotion.data.api.FeedApi
import org.positive.daymotion.data.model.PostFeedRequest
import org.positive.daymotion.data.model.PutEmojiRequest
import org.positive.daymotion.domain.Feed
import org.positive.daymotion.domain.UpdatedEmoji
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val feedApi: FeedApi
) : FeedRepository {

    override fun getFeed(feedId: String): Single<Feed> {
        return feedApi.getFeed(feedId)
            .map {
                Feed(
                    it.id,
                    it.image,
                    it.categoryName,
                    it.missionQuestion,
                    it.user.id,
                    it.user.image,
                    it.user.name ?: it.user.username,
                    UpdatedEmoji(
                        it.emoji.heart,
                        it.emoji.eyes,
                        it.emoji.good,
                        it.emoji.cry
                    )
                )
            }
    }

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
                    it.user.name ?: it.user.username,
                    UpdatedEmoji(
                        it.emoji.heart,
                        it.emoji.eyes,
                        it.emoji.good,
                        it.emoji.cry
                    )
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
                    it.user.name ?: it.user.username,
                    UpdatedEmoji(
                        it.emoji.heart,
                        it.emoji.eyes,
                        it.emoji.good,
                        it.emoji.cry
                    )
                )
            }
        }
    }

    override fun getFeedWithMissionId(missionId: String): Single<List<Feed>> {
        return feedApi.getFeeds(missionId = missionId).map { response ->
            response.content.map {
                Feed(
                    it.id,
                    it.image,
                    it.categoryName,
                    it.missionQuestion,
                    it.user.id,
                    it.user.image,
                    it.user.name ?: it.user.username,
                    UpdatedEmoji(
                        it.emoji.heart,
                        it.emoji.eyes,
                        it.emoji.good,
                        it.emoji.cry
                    )
                )
            }
        }
    }

    override fun postFeed(feedImage: String, missionId: String): Completable {
        return feedApi.postFeeds(
            PostFeedRequest(
                feedImage,
                missionId
            )
        )
    }

    override fun updateEmoji(
        emojis: List<String>,
        feedId: String
    ): Completable {
        return feedApi.putEmoji(feedId, PutEmojiRequest(emojis))
    }
}