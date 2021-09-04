package org.positive.daymotion.data.repository

import io.reactivex.rxjava3.core.Single
import org.positive.daymotion.domain.Feed

interface FeedRepository {

    fun getFeedWithUserId(userId: String): Single<List<Feed>>

    fun getFeedWithCategoryId(categoryId: String): Single<List<Feed>>
}