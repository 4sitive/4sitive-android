package org.positive.daymotion.presentation.home.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import org.positive.daymotion.R

@Parcelize
data class EmojiItem(
    val emojiType: EmojiType,
    val count: Int
) : Parcelable {
    val countStr: String
        get() = count.toString()
}

enum class EmojiType(
    @DrawableRes val resourceId: Int
) {
    HEART(R.drawable.emo_heart),
    EYES(R.drawable.emo_eyes),
    GOOD(R.drawable.emo_good),
    CRY(R.drawable.emo_cry)
}