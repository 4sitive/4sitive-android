package org.positive.daymotion.presentation.my.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.positive.daymotion.domain.UserProfile

@Parcelize
data class UserProfileViewData(
    val id: String,
    val image: String?,
    val introduce: String?,
    val nickName: String
) : Parcelable {
    
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