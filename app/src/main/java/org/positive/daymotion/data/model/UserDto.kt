package org.positive.daymotion.data.model

import org.positive.daymotion.DmConstants
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

data class PutUserRequest(
    val image: String?,
    val introduce: String,
    val name: String
)

data class PutUserResponse(
    val image: String,
    val introduce: String,
    val name: String
) {
    companion object {
        fun to(putUserResponse: PutUserResponse, id: String) = UserProfile(
            id,
            putUserResponse.image,
            putUserResponse.introduce,
            putUserResponse.name,
            putUserResponse.name
        )
    }
}

private fun attachImageBaseUrl(relativePath: String) =
    DmConstants.IMAGE_SERVER_BASE_URL + relativePath