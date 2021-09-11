package org.positive.daymotion.presentation.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.data.repository.FeedRepository
import org.positive.daymotion.presentation.common.base.BaseViewModel
import org.positive.daymotion.presentation.feed.model.EmojiItem
import org.positive.daymotion.presentation.feed.model.FeedInformation
import org.positive.daymotion.presentation.home.model.FeedViewItem
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val feedRepository: FeedRepository
) : BaseViewModel() {

    private val _feed = MutableLiveData<FeedViewItem>()
    val feed: LiveData<FeedViewItem> get() = _feed

    fun getFeed(feedId: String) {
        feedRepository.getFeed(feedId)
            .apiLoadingCompose()
            .autoDispose {
                success {
                    _feed.value = FeedViewItem.of(it)
                }
                error {
                    showErrorMessage(it.message.orEmpty())
                }
            }
    }
}