package org.positive.sms.domain

data class AuthToken(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Int,
    val tokenType: String,
    val scope: List<String>? = null
)