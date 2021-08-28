package org.positive.daymotion.data.repository

import io.reactivex.rxjava3.core.Single
import org.positive.daymotion.domain.UserProfile

interface UserRepository {

    fun getUserProfile(): Single<UserProfile>

    fun putUserProfile(
        id: String,
        image: String?,
        introduce: String,
        name: String
    ): Single<UserProfile>
}