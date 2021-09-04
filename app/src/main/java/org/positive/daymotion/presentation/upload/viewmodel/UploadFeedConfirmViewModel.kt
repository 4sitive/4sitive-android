package org.positive.daymotion.presentation.upload.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.data.pref.AppSharedPreference
import org.positive.daymotion.presentation.common.SingleLiveEvent
import org.positive.daymotion.presentation.common.base.BaseViewModel
import org.positive.daymotion.presentation.upload.model.MissionViewItem
import org.positive.daymotion.presentation.upload.model.TextEditConfig
import javax.inject.Inject

@HiltViewModel
class UploadFeedConfirmViewModel @Inject constructor(
    private val appSharedPreference: AppSharedPreference
) : BaseViewModel() {

    private val _background = MutableLiveData<Any?>()
    val background: LiveData<Any?> get() = _background

    private val _selectedMission = MutableLiveData<MissionViewItem>()
    val selectedMission: LiveData<MissionViewItem> get() = _selectedMission

    private val _textEditConfig = MutableLiveData<TextEditConfig>()
    val textEditConfig: LiveData<TextEditConfig> get() = _textEditConfig

    private val _isTextVisible = MutableLiveData(true)
    val isTextVisible: LiveData<Boolean> get() = _isTextVisible

    private val _goToEdit = SingleLiveEvent<TextEditConfig>()
    val goToEdit: LiveData<TextEditConfig> get() = _goToEdit

    fun toEditMode() {
        val textEditConfig = _textEditConfig.value ?: return
        if (textEditConfig.text.isEmpty()) {
            _goToEdit.value = textEditConfig
        }
    }

    fun updateBackground(background: Any?) {
        _background.value = background
    }

    fun updateSelectedMission(mission: MissionViewItem) {
        _selectedMission.value = mission
    }

    fun updateTextConfig(textEditConfig: TextEditConfig) {
        _textEditConfig.value = textEditConfig
    }

    fun setTextVisible(isVisible: Boolean) {
        _isTextVisible.value = isVisible
    }
}