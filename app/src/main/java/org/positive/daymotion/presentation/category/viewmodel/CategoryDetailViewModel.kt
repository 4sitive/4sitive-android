package org.positive.daymotion.presentation.category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.data.repository.FeedRepository
import org.positive.daymotion.presentation.category.model.DetailQueryType
import org.positive.daymotion.presentation.common.base.BaseViewModel
import org.positive.daymotion.presentation.common.model.FeedThumbnailItem
import javax.inject.Inject

@HiltViewModel
class CategoryDetailViewModel @Inject constructor(
    private val feedRepository: FeedRepository
) : BaseViewModel() {

    private val _categorizedFeedThumbnails = MutableLiveData<List<FeedThumbnailItem>>()
    val categorizedFeedThumbnails: LiveData<List<FeedThumbnailItem>> get() = _categorizedFeedThumbnails

    val isEmptyList: LiveData<Boolean> =
        Transformations.map(_categorizedFeedThumbnails) { items -> items.isEmpty() }

    fun loadFeeds(
        id: String,
        detailQueryType: DetailQueryType
    ) {
        when (detailQueryType) {
            DetailQueryType.MISSION -> loadFeedsOfMission(id)
            DetailQueryType.CATEGORY -> loadFeedsOfCategory(id)
        }
    }

    private fun loadFeedsOfMission(id: String) {
        // TODO
    }

    private fun loadFeedsOfCategory(id: String) {
        feedRepository.getFeedWithCategoryId(id)
            .apiLoadingCompose()
            .autoDispose {
                success { feeds ->
                    _categorizedFeedThumbnails.value = feeds.map {
                        FeedThumbnailItem(
                            missionName = it.missionQuestion,
                            imageUrl = it.feedImage,
                            imageType = FeedThumbnailItem.ImageType.PORTRAIT
                        )
                    }
                }
                error {
                    showErrorMessage(it.message.orEmpty())
                }
            }
    }
}