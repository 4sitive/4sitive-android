package org.positive.daymotion.presentation.upload.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.presentation.common.base.BaseViewModel
import org.positive.daymotion.presentation.upload.model.Mission
import javax.inject.Inject

@HiltViewModel
class UploadFeedEditViewModel @Inject constructor() : BaseViewModel() {

    private val _background = MutableLiveData<Any?>()
    val background: LiveData<Any?> get() = _background

    private val _selectedMission = MutableLiveData<Mission>()
    val selectedMission: LiveData<Mission> get() = _selectedMission

    fun updateBackground(background: Any?) {
        _background.value = background
    }

    fun updateSelectedMission(mission: Mission) {
        _selectedMission.value = mission
    }
}