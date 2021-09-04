package org.positive.daymotion.data.model

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
//    val emoji: Emoji, TODO: emoji
    val author: Boolean
)
data class User(
    val id: String,
    val image: String?,
    val name: String?,
    val username: String
)

data class PostFeedRequest(
    val image: String,
    val missionId: String,
    val requestId: String = "test"
)