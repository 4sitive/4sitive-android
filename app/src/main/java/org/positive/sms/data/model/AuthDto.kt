package org.positive.sms.data.model

import com.google.gson.annotations.SerializedName

data class PostOauthAuthorizationCodeResponse(
    val accessToken: String,
    val refreshToken: String? = null,
    val expiresIn: Int? = null,
    val tokenType: String? = null,
    val scope: List<String>? = null
)

enum class GrantType {
    @SerializedName("authorization_code")
    AUTHORIZATION_CODE,

    @SerializedName("refresh_token")
    REFRESH_TOKEN
}