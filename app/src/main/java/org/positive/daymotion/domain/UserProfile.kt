package org.positive.daymotion.domain

data class UserProfile(
    val id: String,
    val image: String?,
    val introduce: String?,
    val name: String?,
    val userName: String
)