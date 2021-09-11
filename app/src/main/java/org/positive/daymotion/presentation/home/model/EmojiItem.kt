package org.positive.daymotion.presentation.home.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import org.positive.daymotion.R

@Parcelize
data class EmojiItem(
    val emojiType: EmojiType,
    var count: Int
) : Parcelable {
    val countStr: String
        get() = count.toString()
}

enum class EmojiType(
    @DrawableRes val resourceId: Int,
    val rawName: String
) {
    HEART(R.drawable.emo_heart, "heart"),
    EYES(R.drawable.emo_eyes, "eyes"),
    GOOD(R.drawable.emo_good, "good"),
    CRY(R.drawable.emo_cry, "cry")
}