package org.positive.daymotion.presentation.my.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.data.repository.FeedRepository
import org.positive.daymotion.data.repository.UserRepository
import org.positive.daymotion.presentation.common.base.BaseViewModel
import org.positive.daymotion.presentation.common.model.FeedThumbnailItem
import org.positive.daymotion.presentation.my.model.UserProfileViewData
import javax.inject.Inject

@HiltViewModel
class MyTabViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val feedRepository: FeedRepository
) : BaseViewModel() {

    private val _myFeedsThumbnails = MutableLiveData<List<FeedThumbnailItem>>()
    val myFeedsThumbnails: LiveData<List<FeedThumbnailItem>> get() = _myFeedsThumbnails

    val isEmptyList: LiveData<Boolean> =
        Transformations.map(_myFeedsThumbnails) { items -> items.isEmpty() }

    private val _userProfile = MutableLiveData<UserProfileViewData>()
    val userProfile: LiveData<UserProfileViewData> get() = _userProfile

    fun loadMyProfileAndFeeds() {
        userRepository.getUserProfile()
            .flatMap { profile ->
                feedRepository.getFeedWithUserId(profile.id)
                    .map { feeds -> profile to feeds }
            }
            .apiLoadingCompose()
            .autoDispose {
                success { (profile, feeds) ->
                    _userProfile.value = UserProfileViewData.of(profile)
                    _myFeedsThumbnails.value = feeds.map {
                        FeedThumbnailItem(
                            feedId = it.feedId,
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

    fun updateProfile(userProfileViewData: UserProfileViewData) {
        _userProfile.value = userProfileViewData
    }
}