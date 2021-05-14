package org.positive.sms.data.api

import io.reactivex.rxjava3.core.Single
import org.positive.sms.data.model.PostOauthAuthorizationCodeRequest
import org.positive.sms.data.model.PostOauthAuthorizationCodeResponse
import retrofit2.http.POST

interface AuthApi {

    @POST(value = "oauth/token")
    fun postOauthAuthorizationCode(
        postOauthAuthorizationCodeRequest: PostOauthAuthorizationCodeRequest
    ): Single<PostOauthAuthorizationCodeResponse>
}