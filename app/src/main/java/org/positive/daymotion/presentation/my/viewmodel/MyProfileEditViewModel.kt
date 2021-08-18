package org.positive.daymotion.presentation.my.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import dagger.hilt.android.lifecycle.HiltViewModel
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

    val profileImage = MutableLiveData<String?>()

    val name = MutableLiveData<String>()

    val introduce = MutableLiveData<String>()

    val nickNameValidation = Transformations.map(name) { checkNickNameValidation(it) }

    val introduceValidation = Transformations.map(introduce) { it.length < 21 }

    val isProfileUpdatePossible = liveDataMerge(
        introduceValidation,
        nickNameValidation
    ) { introduceValidation, nickNameValidation ->
        introduceValidation == true && nickNameValidation == NickNameValidation.OK
    }

    private var userProfileViewData: UserProfileViewData? = null

    private val _doneProfileUpdate = SingleLiveEvent<UserProfileViewData>()
    val doneProfileUpdate: LiveData<UserProfileViewData> get() = _doneProfileUpdate

    fun initProfile(userProfileViewData: UserProfileViewData) {
        this.userProfileViewData = userProfileViewData
        profileImage.value = userProfileViewData.image
        name.value = userProfileViewData.nickName
        introduce.value = userProfileViewData.introduce.orEmpty()
    }

    fun updateProfile() {
        val origin = userProfileViewData ?: return
        val imageUri = profileImage.value ?: return
        val introduce = introduce.value ?: return
        val name = name.value ?: return

        imageRepository.imageUpload(imageUri)
            .flatMap { userRepository.putUserProfile(origin.id, it, introduce, name) }
            .apiLoadingCompose()
            .autoDispose {
                success {
                    _doneProfileUpdate.value = UserProfileViewData.of(it)
                }
                error {
                    showErrorMessage(it.message.orEmpty())
                }
            }
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