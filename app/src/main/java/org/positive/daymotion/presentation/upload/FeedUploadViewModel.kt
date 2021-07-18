package org.positive.daymotion.presentation.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.presentation.common.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class FeedUploadViewModel @Inject constructor() : BaseViewModel() {

    private val _isToggleAvailable = MutableLiveData(false)
    val isToggleAvailable: LiveData<Boolean> get() = _isToggleAvailable

    fun setToggleAvailable(isAvailable: Boolean) {
        _isToggleAvailable.value = isAvailable
    }
}