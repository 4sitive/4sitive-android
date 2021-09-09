package org.positive.daymotion.presentation.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.data.repository.FeedRepository
import org.positive.daymotion.presentation.common.base.BaseViewModel
import org.positive.daymotion.presentation.home.model.FeedViewItem
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(
    private val feedRepository: FeedRepository
) : BaseViewModel() {

    private val _feeds = MutableLiveData<List<FeedViewItem>>()
    val feeds: LiveData<List<FeedViewItem>> get() = _feeds

    fun loadFeedList(missionId: String) {
        feedRepository.getFeedWithMissionId(missionId)
            .apiLoadingCompose()
            .autoDispose {
                success { feeds ->
                    _feeds.value = feeds.map { FeedViewItem.of(it) }
                }
                error {
                    showErrorMessage(it.message.orEmpty())
                }
            }

    }
}