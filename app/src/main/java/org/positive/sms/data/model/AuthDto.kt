package org.positive.sms.data.model

import com.google.gson.annotations.SerializedName
import org.positive.sms.domain.AuthToken

data class PostOauthAuthorizationCodeResponse(
    val accessToken: String,
    val refreshToken: String? = null,
    val expiresIn: Int? = null,
    val tokenType: String,
    val scope: List<String>? = null
) {

    companion object {

        fun PostOauthAuthorizationCodeResponse.toAuthToken(): AuthToken = AuthToken(
            accessToken = accessToken,
            refreshToken = refreshToken,
            expiresIn = expiresIn,
            tokenType = tokenType,
            scope = scope
        )
    }
}

enum class GrantType {
    @SerializedName("authorization_code")
    AUTHORIZATION_CODE,

    @SerializedName("refresh_token")
    REFRESH_TOKEN
}