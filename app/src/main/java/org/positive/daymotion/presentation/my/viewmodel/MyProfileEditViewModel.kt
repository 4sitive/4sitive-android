package org.positive.daymotion.presentation.my.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.presentation.common.SingleLiveEvent
import org.positive.daymotion.presentation.common.base.BaseViewModel
import org.positive.daymotion.presentation.common.util.liveDataMerge
import org.positive.daymotion.presentation.my.model.NickNameValidation
import javax.inject.Inject

@HiltViewModel
class MyProfileEditViewModel @Inject constructor() : BaseViewModel() {

    val profileImage = MutableLiveData<String>()

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

    private val _doneProfileUpdate = SingleLiveEvent<Nothing>()
    val doneProfileUpdate: LiveData<Nothing> get() = _doneProfileUpdate

    fun initProfile(originProfile: String, originName: String, originIntroduce: String) {
        profileImage.value = originProfile
        name.value = originName
        introduce.value = originIntroduce
    }

    fun updateProfile() {
        _doneProfileUpdate.call()
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