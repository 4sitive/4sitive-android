package org.positive.daymotion.presentation.upload.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.presentation.common.base.BaseViewModel
import org.positive.daymotion.presentation.common.util.liveDataMerge
import org.positive.daymotion.presentation.upload.model.Alignment
import org.positive.daymotion.presentation.upload.model.TextEditConfig
import javax.inject.Inject

@HiltViewModel
class UploadFeedTextEditViewModel @Inject constructor() : BaseViewModel() {

    val isSelectedAlignmentLeft = MutableLiveData(false)
    val isSelectedAlignmentCenter = MutableLiveData(false)
    val isSelectedAlignmentRight = MutableLiveData(false)
    val editingText = MutableLiveData("")

    private val _textColor = MutableLiveData<Int>()
    val textColor: LiveData<Int> = _textColor

    val textAlignment: LiveData<Alignment> = liveDataMerge(
        isSelectedAlignmentLeft,
        isSelectedAlignmentCenter,
        isSelectedAlignmentRight
    ) { i1, i2, i3 ->
        when {
            i1 == true -> Alignment.LEFT
            i2 == true -> Alignment.CENTER
            i3 == true -> Alignment.RIGHT
            else -> throw IllegalStateException("Invalid alignment value")
        }
    }

    private val colorSelectionList = listOf(
        0xFF043EFF.toInt(),
        0xFF000000.toInt(),
        0xFFFFFFFF.toInt(),
        0xFFFF3104.toInt(),
        0xFFFFD504.toInt(),
        0xFF7B30F8.toInt(),
        0xFF01D8D7.toInt()
    )

    fun initWithConfig(textEditConfig: TextEditConfig) {
        when (textEditConfig.alignment) {
            Alignment.LEFT -> isSelectedAlignmentLeft.value = true
            Alignment.CENTER -> isSelectedAlignmentCenter.value = true
            Alignment.RIGHT -> isSelectedAlignmentRight.value = true
        }
        _textColor.value = textEditConfig.textColor
        editingText.value = textEditConfig.text
    }

    fun nextTextColor() {
        val currentColor = _textColor.value ?: return
        val index = colorSelectionList.indexOf(currentColor)
        if (index != -1) {
            val next = if (index == colorSelectionList.lastIndex) 0 else index + 1
            _textColor.value = colorSelectionList[next]
        }
    }
}