package org.positive.daymotion.data.model

import org.positive.daymotion.domain.UserProfile

data class GetUserResponse(
    val id: String,
    val image: String?,
    val introduce: String?,
    val name: String?,
    val username: String
) {
    companion object {
        fun to(getUserResponse: GetUserResponse) = UserProfile(
            getUserResponse.id,
            getUserResponse.image,
            getUserResponse.introduce,
            getUserResponse.name,
            getUserResponse.username
        )
    }
}