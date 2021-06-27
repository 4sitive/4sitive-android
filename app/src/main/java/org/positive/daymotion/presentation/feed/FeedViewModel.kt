package org.positive.daymotion.presentation.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.presentation.common.base.BaseViewModel
import org.positive.daymotion.presentation.feed.model.EmojiItem
import org.positive.daymotion.presentation.feed.model.FeedInformation
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor() : BaseViewModel() {

    private val _feedInformation = MutableLiveData<FeedInformation>()
    val feedInformation: LiveData<FeedInformation> get() = _feedInformation

    fun loadFeedInformation() {
        _feedInformation.value = FeedInformation(
            "",
            true,
            "용훈",
            listOf(
                EmojiItem.Emoji("1", 5.toString()),
                EmojiItem.Emoji("2", 6.toString()),
                EmojiItem.Emoji("3", 7.toString())
            ),
            "#1700192"
        )
    }
}