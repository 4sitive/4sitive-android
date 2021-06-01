package org.positive.sms.data.repository

import io.reactivex.rxjava3.core.Single
import org.positive.sms.domain.AuthToken

interface AuthRepository {

    fun postOauthAuthorizationCode(
        code: String,
        redirectUri: String
    ): Single<AuthToken>

    fun refreshAccessToken(
        refreshToken: String
    ): Single<AuthToken>
}