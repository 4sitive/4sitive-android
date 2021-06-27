package org.positive.daymotion.presentation.feed.model

sealed class EmojiItem {

    data class Emoji(
        val emojiId: String,
        val count: String
    ) : EmojiItem() {
    }

    object AddEmoji : EmojiItem()
}