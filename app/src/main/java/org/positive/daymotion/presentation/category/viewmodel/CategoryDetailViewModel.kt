package org.positive.daymotion.presentation.category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.presentation.base.BaseViewModel
import org.positive.daymotion.presentation.common.model.FeedThumbnailItem
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class CategoryDetailViewModel @Inject constructor() : BaseViewModel() {

    private val _categoryDetails = MutableLiveData<List<FeedThumbnailItem>>()
    val categoryDetails: LiveData<List<FeedThumbnailItem>> get() = _categoryDetails

    val isEmptyDetailItem: LiveData<Boolean> =
        Transformations.map(_categoryDetails) { items -> items.isEmpty() }

    fun loadCategoryDetails() {
        if (Random.nextInt().rem(5) == 0) {
            _categoryDetails.value = emptyList()
        } else {
            _categoryDetails.value = buildList {
                add(FeedThumbnailItem("", FeedThumbnailItem.ImageType.PORTRAIT))
                add(FeedThumbnailItem("", FeedThumbnailItem.ImageType.LANDSCAPE))
                add(FeedThumbnailItem("", FeedThumbnailItem.ImageType.PORTRAIT))
                add(FeedThumbnailItem("", FeedThumbnailItem.ImageType.LANDSCAPE))
            }
        }
    }
}