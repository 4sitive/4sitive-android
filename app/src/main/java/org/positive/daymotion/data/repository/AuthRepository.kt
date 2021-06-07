package org.positive.daymotion.data.repository

import io.reactivex.rxjava3.core.Single
import org.positive.daymotion.domain.AuthToken

interface AuthRepository {

    fun postOauthAuthorizationCode(
        code: String,
        redirectUri: String
    ): Single<AuthToken>

    fun refreshAccessToken(
        refreshToken: String
    ): Single<AuthToken>
}