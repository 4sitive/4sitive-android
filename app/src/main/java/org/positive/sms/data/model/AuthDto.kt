package org.positive.sms.data.model

import com.google.gson.annotations.SerializedName

data class PostOauthAuthorizationCodeRequest(
    @SerializedName("code")
    val code: String,
    @SerializedName("grant_type")
    val grantType: GrantType,
    @SerializedName("redirect_uri")
    val redirectUri: String,
    @SerializedName("client_id")
    val clientId: String
)

data class PostOauthAuthorizationCodeResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String? = null,
    @SerializedName("expires_in")
    val expiresInSeconds: Int? = null,
    @SerializedName("token_type")
    val tokenType: String? = null,
    @SerializedName("scope")
    val scope: List<String>? = null
)

enum class GrantType {
    @SerializedName("authorization_code")
    AUTHORIZATION_CODE,

    @SerializedName("refresh_token")
    REFRESH_TOKEN
}