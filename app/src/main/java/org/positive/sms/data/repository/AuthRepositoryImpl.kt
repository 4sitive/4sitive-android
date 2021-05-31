package org.positive.sms.data.repository

import io.reactivex.rxjava3.core.Single
import org.positive.sms.data.api.AuthApi
import org.positive.sms.data.model.GrantType
import org.positive.sms.data.model.OauthAuthorizationResponse.Companion.toAuthToken
import org.positive.sms.domain.AuthToken
import org.positive.sms.extension.declaredSerializedName
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {

    override fun postOauthAuthorizationCode(
        code: String,
        redirectUri: String
    ): Single<AuthToken> = authApi.postOauthAuthorizationCode(
        code,
        GrantType.AUTHORIZATION_CODE.declaredSerializedName(),
        redirectUri
    ).map { it.toAuthToken() }

    override fun refreshAccessToken(
        refreshToken: String
    ): Single<AuthToken> = authApi.refreshAccessToken(
        GrantType.REFRESH_TOKEN.declaredSerializedName(),
        refreshToken
    ).map { it.toAuthToken() }
}