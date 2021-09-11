package org.positive.daymotion.data.model

data class GetFeedResponse(
    val id: String,
    val image: String?,
    val categoryId: String,
    val categoryName: String,
    val missionId: String,
    val missionQuestion: String,
    val user: User,
    val emoji: Emoji,
    val author: Boolean
)

data class GetFeedsResponse(
    val content: List<Feed>,
    val token: String
)

data class Feed(
    val id: String,
    val image: String?,
    val categoryName: String,
    val missionQuestion: String,
    val user: User,
    val emoji: Emoji,
    val author: Boolean
)

data class User(
    val id: String,
    val image: String?,
    val name: String?,
    val username: String
)

data class Emoji(
    val heart: Int?,
    val eyes: Int?,
    val good: Int?,
    val cry: Int?,
)

data class PostFeedRequest(
    val image: String,
    val missionId: String,
    val requestId: String = "test"
)

data class PutEmojiRequest(
    val emoji: List<String>
)

data class PutEmojiResponse(
    val emoji: List<String>
)