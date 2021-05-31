package org.positive.sms.domain

data class AuthToken(
    val accessToken: String,
    val refreshToken: String? = null,
    val expiresIn: Int? = null,
    val tokenType: String,
    val scope: List<String>? = null
)