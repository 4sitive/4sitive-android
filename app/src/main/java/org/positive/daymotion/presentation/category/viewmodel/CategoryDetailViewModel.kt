package org.positive.daymotion.presentation.category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.presentation.common.base.BaseViewModel
import org.positive.daymotion.presentation.common.model.FeedThumbnailItem
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class CategoryDetailViewModel @Inject constructor() : BaseViewModel() {

    private val _categorizedFeedThumbnails = MutableLiveData<List<FeedThumbnailItem>>()
    val categorizedFeedThumbnails: LiveData<List<FeedThumbnailItem>> get() = _categorizedFeedThumbnails

    val isEmptyList: LiveData<Boolean> =
        Transformations.map(_categorizedFeedThumbnails) { items -> items.isEmpty() }

    fun loadCategorizedFeed() {
        if (Random.nextInt().rem(5) == 0) {
            _categorizedFeedThumbnails.value = emptyList()
        } else {
            _categorizedFeedThumbnails.value = buildList {
                add(FeedThumbnailItem("mission1", "", FeedThumbnailItem.ImageType.PORTRAIT))
                add(FeedThumbnailItem("mission1", "", FeedThumbnailItem.ImageType.LANDSCAPE))
                add(FeedThumbnailItem("mission1", "", FeedThumbnailItem.ImageType.PORTRAIT))
                add(FeedThumbnailItem("mission1", "", FeedThumbnailItem.ImageType.LANDSCAPE))
            }
        }
    }
}