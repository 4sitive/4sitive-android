package org.positive.daymotion.presentation.home.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedViewItem(
    val title: String,
    val image: String,
    val isLandscapeImage: Boolean,
    val author: String,
    val emoji: List<EmojiItem>
) : Parcelable