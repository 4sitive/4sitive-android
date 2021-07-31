package org.positive.daymotion.presentation.home.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmojiItem(
    val emojiId: String,
    val count: String
) : Parcelable
