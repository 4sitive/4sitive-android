package org.positive.daymotion.presentation.my.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.positive.daymotion.domain.UserProfile

@Parcelize
data class UserProfileViewData(
    val id: String,
    val image: ProfileImage,
    val introduce: String?,
    val nickName: String
) : Parcelable {

    companion object {
        fun of(userProfile: UserProfile): UserProfileViewData {
            return UserProfileViewData(
                userProfile.id,
                ProfileImage(userProfile.image, null),
                userProfile.introduce.orEmpty(),
                userProfile.name ?: userProfile.userName
            )
        }

        fun of(userProfile: UserProfile, localImage: String?): UserProfileViewData {
            return UserProfileViewData(
                userProfile.id,
                ProfileImage(userProfile.image, localImage),
                userProfile.introduce.orEmpty(),
                userProfile.name ?: userProfile.userName
            )
        }
    }

    @Parcelize
    data class ProfileImage(
        val remote: String?,
        val local: String?
    ) : Parcelable {

        val url
            get() = if(local == null) {
                remote
            } else {
                local
            }
    }
}