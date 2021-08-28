package org.positive.daymotion.presentation.my.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import org.positive.daymotion.data.repository.ImageRepository
import org.positive.daymotion.data.repository.UserRepository
import org.positive.daymotion.presentation.common.SingleLiveEvent
import org.positive.daymotion.presentation.common.base.BaseViewModel
import org.positive.daymotion.presentation.common.util.liveDataMerge
import org.positive.daymotion.presentation.my.model.NickNameValidation
import org.positive.daymotion.presentation.my.model.UserProfileViewData
import javax.inject.Inject

@HiltViewModel
class MyProfileEditViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val imageRepository: ImageRepository
) : BaseViewModel() {

    private val _profileImage = MutableLiveData<UserProfileViewData.ProfileImage>()
    val profileImage: LiveData<UserProfileViewData.ProfileImage> get() = _profileImage

    val name = MutableLiveData<String>()

    val introduce = MutableLiveData<String>()

    private val _nickNameValidation = Transformations.map(name) { checkNickNameValidation(it) }
    val nickNameValidation: LiveData<NickNameValidation> get() = _nickNameValidation

    val introduceValidation = Transformations.map(introduce) { it.length < 21 }

    val isProfileUpdatePossible = liveDataMerge(
        introduceValidation,
        nickNameValidation
    ) { introduceValidation, nickNameValidation ->
        introduceValidation == true && nickNameValidation == NickNameValidation.OK
    }

    private var userId: String? = null

    private val _doneProfileUpdate = SingleLiveEvent<UserProfileViewData>()
    val doneProfileUpdate: LiveData<UserProfileViewData> get() = _doneProfileUpdate

    fun initProfile(userProfileViewData: UserProfileViewData) {
        userId = userProfileViewData.id
        _profileImage.value = userProfileViewData.image
        name.value = userProfileViewData.nickName
        introduce.value = userProfileViewData.introduce.orEmpty()
    }

    fun updateProfile() {
        val id = userId ?: return
        val introduce = introduce.value ?: return
        val name = name.value ?: return
        val image = profileImage.value ?: return

        val imageUrlSingle = if (image.local == null) {
            Single.just(image.remote)
        } else {
            imageRepository.imageUpload(image.local)
        }

        imageUrlSingle
            .flatMap { imageUrl ->
                userRepository.putUserProfile(id, imageUrl, introduce, name)
            }
            .apiLoadingCompose()
            .autoDispose {
                success {
                    _doneProfileUpdate.value = UserProfileViewData.of(it, image.local)
                }
                error {
                    showErrorMessage(it.message.orEmpty())
                }
            }
    }

    fun updateProfileImage(image: String) {
        val old = profileImage.value ?: return
        _profileImage.value = old.copy(local = image)
    }

    private fun checkNickNameValidation(input: String) = when {
        nickNameValidateRegex.matchEntire(input) == null -> NickNameValidation.DENIED_SPECIAL_CHAR
        input.length > 8 -> NickNameValidation.DENIED_TOO_LONG
        else -> NickNameValidation.OK
    }

    companion object {
        private val nickNameValidateRegex = Regex("^[a-zA-Z0-9가-힉ㄱ-ㅎㅏ-ㅣ]*$")
    }
}