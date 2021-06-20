package org.positive.daymotion.presentation.my.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.common.SingleLiveEvent
import org.positive.daymotion.presentation.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MyProfileEditViewModel @Inject constructor() : BaseViewModel() {

    val name = MutableLiveData<String>()
    val introduce = MutableLiveData<String>()

    private val _doneProfileUpdate = SingleLiveEvent<Nothing>()
    val doneProfileUpdate: LiveData<Nothing> get() = _doneProfileUpdate

    fun initProfile(originName: String, originIntroduce: String) {
        name.value = originName
        introduce.value = originIntroduce
    }

    fun updateProfile() {
        _doneProfileUpdate.call()
    }
}