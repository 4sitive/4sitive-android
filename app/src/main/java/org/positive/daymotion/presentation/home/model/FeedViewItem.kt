package org.positive.daymotion.presentation.home.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedViewItem(
    val title: String,
    @DrawableRes val image: Int, // TODO: replace url
    val isLandscapeImage: Boolean,
    val author: String,
    val emojis: List<EmojiItem>
) : Parcelable