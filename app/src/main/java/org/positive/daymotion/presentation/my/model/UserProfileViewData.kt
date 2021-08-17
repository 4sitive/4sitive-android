package org.positive.daymotion.presentation.my.model

import org.positive.daymotion.domain.UserProfile

data class UserProfileViewData(
    val id: String,
    val image: String?,
    val introduce: String?,
    val nickName: String
) {
    companion object {
        fun of(userProfile: UserProfile): UserProfileViewData {
            return UserProfileViewData(
                userProfile.id,
                userProfile.image,
                userProfile.introduce,
                userProfile.name ?: userProfile.userName
            )
        }
    }
}