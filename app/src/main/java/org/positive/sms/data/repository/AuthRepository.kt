package org.positive.sms.data.repository

import io.reactivex.rxjava3.core.Single
import org.positive.sms.data.model.GrantType
import org.positive.sms.data.model.PostOauthAuthorizationCodeResponse
import org.positive.sms.domain.AuthToken

interface AuthRepository {

    fun postOauthAuthorizationCode(
        code: String,
        grantType: GrantType,
        redirectUri: String
    ): Single<AuthToken>
}