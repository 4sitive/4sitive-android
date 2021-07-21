package org.positive.daymotion.presentation.upload.viewmodel

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.presentation.common.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class UploadFeedTextEditViewModel @Inject constructor() : BaseViewModel() {

    val isSelectedAlignmentLeft = MutableLiveData<Boolean>()
    val isSelectedAlignmentCenter = MutableLiveData<Boolean>()
    val isSelectedAlignmentRight = MutableLiveData<Boolean>()
}