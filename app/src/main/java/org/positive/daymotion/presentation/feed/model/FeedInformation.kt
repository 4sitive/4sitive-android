package org.positive.daymotion.presentation.feed.model

import android.graphics.Color
import androidx.annotation.ColorInt
import org.positive.daymotion.R

data class FeedInformation(
    val imageUrl: String,
    val isLandscapeImage: Boolean,
    val author: String,
    val emojis: List<EmojiItem>,
    val keyColor: String
) {
    @get:ColorInt
    val keyColorResource = try {
        Color.parseColor(keyColor)
    } catch (e: IllegalArgumentException) {
        R.color._000000
    }
}