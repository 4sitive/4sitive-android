package org.positive.daymotion.data.repository

import io.reactivex.rxjava3.core.Single
import org.positive.daymotion.data.api.UserApi
import org.positive.daymotion.data.model.GetUserResponse
import org.positive.daymotion.domain.UserProfile
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi
) : UserRepository {

    override fun getUserProfile(): Single<UserProfile> = userApi.getUser().map {
        GetUserResponse.to(it)
    }
}