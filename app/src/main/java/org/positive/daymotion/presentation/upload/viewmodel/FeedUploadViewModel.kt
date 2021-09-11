package org.positive.daymotion.presentation.upload.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.R
import org.positive.daymotion.data.repository.FeedRepository
import org.positive.daymotion.data.repository.ImageRepository
import org.positive.daymotion.data.repository.MissionRepository
import org.positive.daymotion.presentation.common.SingleLiveEvent
import org.positive.daymotion.presentation.common.base.BaseViewModel
import org.positive.daymotion.presentation.upload.model.BackgroundSelection
import org.positive.daymotion.presentation.upload.model.MissionViewItem
import org.positive.daymotion.presentation.upload.model.Mode
import javax.inject.Inject

@HiltViewModel
class FeedUploadViewModel @Inject constructor(
    private val missionRepository: MissionRepository,
    private val feedRepository: FeedRepository,
    private val imageRepository: ImageRepository
) : BaseViewModel() {

    private val _todayMissions = MutableLiveData<List<MissionViewItem>>()
    val todayMissions: LiveData<List<MissionViewItem>> get() = _todayMissions

    private val _selectedMission = MutableLiveData<MissionViewItem>()
    val selectedMission: LiveData<MissionViewItem> get() = _selectedMission

    private val _showMissionList = SingleLiveEvent<Pair<MissionViewItem, List<MissionViewItem>>>()
    val showMissionList: LiveData<Pair<MissionViewItem, List<MissionViewItem>>> get() = _showMissionList

    private val _isToggleAvailable = MutableLiveData(false)
    val isToggleAvailable: LiveData<Boolean> get() = _isToggleAvailable

    private val _selections = MutableLiveData<List<BackgroundSelection>>()
    val selections: LiveData<List<BackgroundSelection>> get() = _selections

    private val _mode = MutableLiveData(Mode.CAMERA)
    val mode: LiveData<Mode> get() = _mode

    private val _selectedBackgroundSelection = MutableLiveData<BackgroundSelection>()
    val selectedBackgroundSelection: LiveData<BackgroundSelection> get() = _selectedBackgroundSelection

    private val _finish = SingleLiveEvent<Nothing>()
    val finish: LiveData<Nothing> get() = _finish

    private val _uploadDone = SingleLiveEvent<Nothing>()
    val uploadDone: LiveData<Nothing> get() = _uploadDone

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

    fun loadTodayMissions(missionId: String?) {
        missionRepository.loadTodayMissions()
            .apiLoadingCompose()
            .autoDispose {
                success { missions ->
                    val todayMissions = missions.map {
                        MissionViewItem(
                            it.id,
                            it.categoryName,
                            it.question
                        )
                    }
                    _todayMissions.value = todayMissions
                    _selectedMission.value = todayMissions.firstOrNull {
                        it.id == missionId
                    } ?: todayMissions[0]
                }
            }
    }

    fun setToggleAvailable(isAvailable: Boolean) {
        _isToggleAvailable.value = isAvailable
    }

    fun changedBackgroundSelection(newPosition: Int) {
        val item = _selections.value?.getOrNull(newPosition) ?: return
        _selectedBackgroundSelection.value = item
        when (item) {
            is BackgroundSelection.Camera -> _mode.value = Mode.CAMERA
            is BackgroundSelection.Custom -> _mode.value = Mode.CONFIRM
            is BackgroundSelection.Default -> _mode.value = Mode.CONFIRM
        }
    }

    fun selectCustomImage(custom: BackgroundSelection.Custom) {
        _selectedBackgroundSelection.value = custom
        _mode.value = Mode.CONFIRM
        _selections.value = mutableListOf(custom) + constSelections
    }

    fun close() {
        val selected = _selectedBackgroundSelection.value
        if (_mode.value == Mode.CONFIRM && selected is BackgroundSelection.Custom) {
            _mode.value = Mode.CAMERA
            _selections.value = mutableListOf(BackgroundSelection.Camera) + constSelections
        } else {
            _finish.call()
        }
    }

    fun showMissionList() {
        val selected = _selectedMission.value ?: return
        val missions = _todayMissions.value ?: return
        _showMissionList.value = selected to missions
    }

    fun selectMission(mission: MissionViewItem) {
        _selectedMission.value = mission
    }

    fun uploadFeed(uri: String) {
        val mission = _selectedMission.value ?: return

        imageRepository.imageUpload(uri)
            .flatMapCompletable { feedRepository.postFeed(it, mission.id) }
            .apiLoadingCompose()
            .autoDispose {
                complete {
                    _uploadDone.call()
                }
                error {
                    showErrorMessage(it.message.orEmpty())
                }
            }
    }

}