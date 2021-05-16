package org.positive.sms.data.api

import io.reactivex.rxjava3.core.Single
import org.positive.sms.data.model.PostOauthAuthorizationCodeResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    @FormUrlEncoded
    @POST(value = "oauth/token")
    fun postOauthAuthorizationCode(
        @Field("code") code: String,
        @Field("grant_type") grantType: String,
        @Field("redirect_uri") redirectUri: String
    ): Single<PostOauthAuthorizationCodeResponse>
}