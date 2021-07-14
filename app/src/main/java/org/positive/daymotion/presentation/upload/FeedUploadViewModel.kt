package org.positive.daymotion.presentation.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.presentation.common.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class FeedUploadViewModel @Inject constructor() : BaseViewModel() {

    private val _isCameraReady = MutableLiveData(false)
    val isCameraReady: LiveData<Boolean> get() = _isCameraReady

    private val _isToggleAvailable = MutableLiveData(false)
    val isToggleAvailable: LiveData<Boolean> get() = _isToggleAvailable

    fun setReadyCamera() {
        _isCameraReady.value = true
    }

    fun setToggleAvailable() {
        _isToggleAvailable.value = true
    }
}