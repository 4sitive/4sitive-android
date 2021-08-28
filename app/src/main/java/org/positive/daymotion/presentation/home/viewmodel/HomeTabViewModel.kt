package org.positive.daymotion.presentation.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.data.repository.MissionRepository
import org.positive.daymotion.data.repository.UserRepository
import org.positive.daymotion.domain.UserProfile
import org.positive.daymotion.presentation.common.base.BaseViewModel
import org.positive.daymotion.presentation.home.model.MissionViewItem
import javax.inject.Inject

@HiltViewModel
class HomeTabViewModel @Inject constructor(
    private val missionRepository: MissionRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _todayMissions = MutableLiveData<List<MissionViewItem>>()
    val todayMissions: LiveData<List<MissionViewItem>> get() = _todayMissions

    private val _userProfile = MutableLiveData<UserProfile>()
    val userProfile: LiveData<UserProfile> get() = _userProfile

    fun loadTodayMissions() {
        _todayMissions.value = missionRepository.loadTodayMissions()
            .mapIndexed { i, mission -> MissionViewItem.of(mission, i) }
    }

    fun loadUserProfile() {
        userRepository.getUserProfile()
            .apiLoadingCompose()
            .autoDispose {
                success {
                    _userProfile.value = it
                }
                error {
                    showErrorMessage(it.message.orEmpty())
                }
            }
    }
}