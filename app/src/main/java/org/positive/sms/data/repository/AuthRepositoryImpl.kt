package org.positive.sms.data.repository

import io.reactivex.rxjava3.core.Single
import org.positive.sms.data.api.AuthApi
import org.positive.sms.data.model.GrantType
import org.positive.sms.data.model.PostOauthAuthorizationCodeResponse
import org.positive.sms.extension.declaredSerializedName
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {

    override fun postOauthAuthorizationCode(
        code: String,
        grantType: GrantType,
        redirectUri: String
    ): Single<PostOauthAuthorizationCodeResponse> = authApi.postOauthAuthorizationCode(
        code,
        grantType.declaredSerializedName(),
        redirectUri
    )
}