package org.positive.daymotion.data.repository

import io.reactivex.rxjava3.core.Single
import org.positive.daymotion.data.api.UserApi
import org.positive.daymotion.data.model.GetUserResponse
import org.positive.daymotion.data.model.PutUserRequest
import org.positive.daymotion.data.model.PutUserResponse
import org.positive.daymotion.domain.UserProfile
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi
) : UserRepository {

    override fun getUserProfile(): Single<UserProfile> = userApi.getUser().map {
        GetUserResponse.to(it)
    }

    override fun putUserProfile(
        id: String,
        image: String?,
        introduce: String,
        name: String
    ): Single<UserProfile> {
        val request = PutUserRequest(image, introduce, name)
        return userApi.putUser(request).map { PutUserResponse.to(it, id) }
    }
}