package org.positive.daymotion.data.model

import com.google.gson.annotations.SerializedName
import org.positive.daymotion.domain.AuthToken

data class OauthAuthorizationResponse(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Int,
    val tokenType: String,
    val scope: List<String>? = null
) {

    companion object {
        fun OauthAuthorizationResponse.toAuthToken(): AuthToken = AuthToken(
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