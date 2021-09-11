package org.positive.daymotion.domain

data class Feed(
    val feedId: String,
    val feedImage: String?,
    val categoryName: String,
    val missionQuestion: String,
    val authorId: String,
    val authorProfileImage: String?,
    val authorName: String,
    val emojis: UpdatedEmoji
)