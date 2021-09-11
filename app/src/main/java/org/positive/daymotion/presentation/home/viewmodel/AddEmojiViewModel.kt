package org.positive.daymotion.presentation.home.viewmodel

import androidx.lifecycle.LiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.data.repository.FeedRepository
import org.positive.daymotion.presentation.common.SingleLiveEvent
import org.positive.daymotion.presentation.common.base.BaseViewModel
import org.positive.daymotion.presentation.home.model.EmojiItem
import org.positive.daymotion.presentation.home.model.EmojiType
import javax.inject.Inject

@HiltViewModel
class AddEmojiViewModel @Inject constructor(
    private val feedRepository: FeedRepository
) : BaseViewModel() {

    private val _updatedEmojis = SingleLiveEvent<List<EmojiItem>>()
    val updatedEmojis: LiveData<List<EmojiItem>> get() = _updatedEmojis

    private var feedId: String = ""
    private var emojis: List<EmojiItem> = emptyList()

    fun setupData(feedId: String, emojis: List<EmojiItem>) {
        this.feedId = feedId
        this.emojis = emojis
    }

    fun addEmoji(addType: EmojiType) {
        val requestEmoji = emojis.map {
            it.emojiType.rawName
        }.toMutableSet()
        requestEmoji.add(addType.rawName)

        feedRepository.updateEmoji(requestEmoji.toList(), feedId)
            .apiLoadingCompose()
            .autoDispose {
                complete {
                    val emojiItem = emojis.find { it.emojiType == addType }
                    if (emojiItem != null) {
                        _updatedEmojis.value = emojis
                    } else {
                        _updatedEmojis.value = emojis.toMutableList().apply {
                            add(EmojiItem(addType, 1))
                        }
                    }
                }
                error {
                    showErrorMessage(it.message.orEmpty())
                }
            }
    }
}