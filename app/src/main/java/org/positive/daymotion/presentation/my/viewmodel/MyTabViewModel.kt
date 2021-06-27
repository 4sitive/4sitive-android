package org.positive.daymotion.presentation.my.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.presentation.common.base.BaseViewModel
import org.positive.daymotion.presentation.common.model.FeedThumbnailItem
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MyTabViewModel @Inject constructor() : BaseViewModel() {

    private val _myFeedsThumbnails = MutableLiveData<List<FeedThumbnailItem>>()
    val myFeedsThumbnails: LiveData<List<FeedThumbnailItem>> get() = _myFeedsThumbnails

    val isEmptyList: LiveData<Boolean> =
        Transformations.map(_myFeedsThumbnails) { items -> items.isEmpty() }

    fun loadMyFeeds() {
        if (Random.nextInt().rem(5) == 0) {
            _myFeedsThumbnails.value = emptyList()
        } else {
            _myFeedsThumbnails.value = buildList {
                add(FeedThumbnailItem("mission1", "", FeedThumbnailItem.ImageType.PORTRAIT))
                add(FeedThumbnailItem("mission1", "", FeedThumbnailItem.ImageType.LANDSCAPE))
                add(FeedThumbnailItem("mission1", "", FeedThumbnailItem.ImageType.PORTRAIT))
                add(FeedThumbnailItem("mission1", "", FeedThumbnailItem.ImageType.LANDSCAPE))
                add(FeedThumbnailItem("mission1", "", FeedThumbnailItem.ImageType.PORTRAIT))
                add(FeedThumbnailItem("mission1", "", FeedThumbnailItem.ImageType.LANDSCAPE))
                add(FeedThumbnailItem("mission1", "", FeedThumbnailItem.ImageType.PORTRAIT))
                add(FeedThumbnailItem("mission1", "", FeedThumbnailItem.ImageType.LANDSCAPE))
                add(FeedThumbnailItem("mission1", "", FeedThumbnailItem.ImageType.PORTRAIT))
                add(FeedThumbnailItem("mission1", "", FeedThumbnailItem.ImageType.LANDSCAPE))
                add(FeedThumbnailItem("mission1", "", FeedThumbnailItem.ImageType.PORTRAIT))
                add(FeedThumbnailItem("mission1", "", FeedThumbnailItem.ImageType.LANDSCAPE))
            }
        }
    }
}