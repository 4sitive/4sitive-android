package org.positive.daymotion.presentation.home.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.positive.daymotion.domain.Feed
import org.positive.daymotion.domain.UpdatedEmoji

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
                toEmojis(feed.emojis)
            )
        }

        private fun toEmojis(updatedEmoji: UpdatedEmoji): List<EmojiItem> {
            val emojis = mutableListOf<EmojiItem>()
            updatedEmoji.heart?.let {
                emojis.add(EmojiItem(EmojiType.HEART, it))
            }
            updatedEmoji.eyes?.let {
                emojis.add(EmojiItem(EmojiType.EYES, it))
            }
            updatedEmoji.good?.let {
                emojis.add(EmojiItem(EmojiType.GOOD, it))
            }
            updatedEmoji.cry?.let {
                emojis.add(EmojiItem(EmojiType.CRY, it))
            }
            return emojis
        }
    }
}