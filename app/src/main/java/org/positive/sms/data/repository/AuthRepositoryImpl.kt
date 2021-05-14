package org.positive.sms.data.repository

import io.reactivex.rxjava3.core.Single
import org.positive.sms.data.model.PostOauthAuthorizationCodeRequest
import org.positive.sms.data.model.PostOauthAuthorizationCodeResponse
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRepository: AuthRepository
) : AuthRepository {

    override fun postOauthAuthorizationCode(
        postOauthAuthorizationCodeRequest: PostOauthAuthorizationCodeRequest
    ): Single<PostOauthAuthorizationCodeResponse> =
        authRepository.postOauthAuthorizationCode(postOauthAuthorizationCodeRequest)
}