package org.positive.daymotion.presentation.home.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.positive.daymotion.domain.Feed

@Parcelize
data class FeedViewItem(
    val id: String,
    val title: String,
    val image: String?,
    val isLandscapeImage: Boolean,
    val author: String,
    val authorProfile: String?,
    val emojis: List<EmojiItem>
) : Parcelable {

    companion object {
        fun of(feed: Feed): FeedViewItem {
            return FeedViewItem(
                feed.feedId,
                feed.missionQuestion,
                feed.feedImage,
                false,
                feed.authorName,
                feed.authorProfileImage,
                emptyList()
            )
        }
    }
}