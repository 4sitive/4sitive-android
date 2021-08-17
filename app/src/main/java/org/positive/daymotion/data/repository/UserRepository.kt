package org.positive.daymotion.data.repository

import io.reactivex.rxjava3.core.Single
import org.positive.daymotion.domain.UserProfile

interface UserRepository {

    fun getUserProfile(): Single<UserProfile>
}