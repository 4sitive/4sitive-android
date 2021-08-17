package org.positive.daymotion.presentation.my.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.data.repository.UserRepository
import org.positive.daymotion.presentation.common.base.BaseViewModel
import org.positive.daymotion.presentation.common.model.FeedThumbnailItem
import org.positive.daymotion.presentation.my.model.UserProfileViewData
import javax.inject.Inject

@HiltViewModel
class MyTabViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _myFeedsThumbnails = MutableLiveData<List<FeedThumbnailItem>>()
    val myFeedsThumbnails: LiveData<List<FeedThumbnailItem>> get() = _myFeedsThumbnails

    val isEmptyList: LiveData<Boolean> =
        Transformations.map(_myFeedsThumbnails) { items -> items.isEmpty() }

    private val _userProfile = MutableLiveData<UserProfileViewData>()
    val userProfile: LiveData<UserProfileViewData> get() = _userProfile

    fun loadMyProfile() {
        userRepository.getUserProfile()
            .apiLoadingCompose()
            .autoDispose {
                success {
                    _userProfile.value = UserProfileViewData.of(it)
                }
                error {
                    showErrorMessage(it.message.orEmpty())
                }
            }
    }

    fun loadMyFeeds() {
        _myFeedsThumbnails.value = emptyList()
//        if (Random.nextInt().rem(5) == 0) {
//            _myFeedsThumbnails.value = emptyList()
//        } else {
//            _myFeedsThumbnails.value = buildList {
//                add(FeedThumbnailItem("mission1", "", FeedThumbnailItem.ImageType.PORTRAIT))
//                add(FeedThumbnailItem("mission1", "", FeedThumbnailItem.ImageType.LANDSCAPE))
//                add(FeedThumbnailItem("mission1", "", FeedThumbnailItem.ImageType.PORTRAIT))
//                add(FeedThumbnailItem("mission1", "", FeedThumbnailItem.ImageType.LANDSCAPE))
//                add(FeedThumbnailItem("mission1", "", FeedThumbnailItem.ImageType.PORTRAIT))
//                add(FeedThumbnailItem("mission1", "", FeedThumbnailItem.ImageType.LANDSCAPE))
//                add(FeedThumbnailItem("mission1", "", FeedThumbnailItem.ImageType.PORTRAIT))
//                add(FeedThumbnailItem("mission1", "", FeedThumbnailItem.ImageType.LANDSCAPE))
//                add(FeedThumbnailItem("mission1", "", FeedThumbnailItem.ImageType.PORTRAIT))
//                add(FeedThumbnailItem("mission1", "", FeedThumbnailItem.ImageType.LANDSCAPE))
//                add(FeedThumbnailItem("mission1", "", FeedThumbnailItem.ImageType.PORTRAIT))
//                add(FeedThumbnailItem("mission1", "", FeedThumbnailItem.ImageType.LANDSCAPE))
//            }
//        }
    }
}