package org.positive.daymotion.presentation.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.R
import org.positive.daymotion.presentation.common.base.BaseViewModel
import org.positive.daymotion.presentation.upload.model.BackgroundSelection
import org.positive.daymotion.presentation.upload.model.Mode
import javax.inject.Inject

@HiltViewModel
class FeedUploadViewModel @Inject constructor() : BaseViewModel() {

    private val _isToggleAvailable = MutableLiveData(false)
    val isToggleAvailable: LiveData<Boolean> get() = _isToggleAvailable

    private val _selections = MutableLiveData<List<BackgroundSelection>>()
    val selections: LiveData<List<BackgroundSelection>> get() = _selections

    private val _mode = MutableLiveData(Mode.CAMERA)
    val mode: LiveData<Mode> get() = _mode

    private val _selected = MutableLiveData<BackgroundSelection>()
    val selected: LiveData<BackgroundSelection> get() = _selected

    private val constSelections = listOf(
        BackgroundSelection.Default(R.drawable.img_feed_thumb_01, R.drawable.img_feed_01),
        BackgroundSelection.Default(R.drawable.img_feed_thumb_02, R.drawable.img_feed_02),
        BackgroundSelection.Default(R.drawable.img_feed_thumb_03, R.drawable.img_feed_03),
        BackgroundSelection.Default(R.drawable.img_feed_thumb_04, R.drawable.img_feed_04),
        BackgroundSelection.Default(R.drawable.img_feed_thumb_05, R.drawable.img_feed_05),
        BackgroundSelection.Default(R.drawable.img_feed_thumb_06, R.drawable.img_feed_06),
        BackgroundSelection.Default(R.drawable.img_feed_thumb_07, R.drawable.img_feed_07),
        BackgroundSelection.Default(R.drawable.img_feed_thumb_08, R.drawable.img_feed_08)
    )

    init {
        _selections.value = mutableListOf(BackgroundSelection.Camera) + constSelections
    }


    fun setToggleAvailable(isAvailable: Boolean) {
        _isToggleAvailable.value = isAvailable
    }

    fun changedSelection(newPosition: Int) {
        val item = _selections.value?.getOrNull(newPosition) ?: return
        _selected.value = item
        when (item) {
            is BackgroundSelection.Camera -> _mode.value = Mode.CAMERA
            is BackgroundSelection.Custom -> _mode.value = Mode.EDIT
            is BackgroundSelection.Default -> _mode.value = Mode.EDIT
        }
    }

    fun selectCustomImage(custom: BackgroundSelection.Custom) {
        _selected.value = custom
        _selections.value = mutableListOf(custom) + constSelections
    }
}