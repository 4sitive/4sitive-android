package org.positive.daymotion.presentation.setting

import androidx.lifecycle.LiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.data.pref.AppSharedPreference
import org.positive.daymotion.presentation.common.SingleLiveEvent
import org.positive.daymotion.presentation.common.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SettingTabViewModel @Inject constructor(
    private val appSharedPreference: AppSharedPreference
) : BaseViewModel() {

    private val _logoutComplete = SingleLiveEvent<Nothing>()
    val logoutComplete: LiveData<Nothing> get() = _logoutComplete

    private val _secessionComplete = SingleLiveEvent<Nothing>()
    val secessionComplete: LiveData<Nothing> get() = _secessionComplete

    fun logout() {
        // TODO(je): logout api
        appSharedPreference.clearAuthToken()
            .apiLoadingCompose()
            .autoDispose {
                complete {
                    _logoutComplete.call()
                }
                error {
                    showErrorMessage(it.message.orEmpty())
                }
            }
    }

    fun secession() {
        // TODO(je): secession api
        _secessionComplete.call()
    }
}