package org.positive.daymotion.data.model

data class GetLastMissionsResponse(
    val now: String,
    val content: List<Mission>
) {
    data class Mission(
        val id: String,
        val image: String?,
        val question: String?,
        val content: String?,
        val date: String?
    )
}