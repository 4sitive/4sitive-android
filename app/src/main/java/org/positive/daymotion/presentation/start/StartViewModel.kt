package org.positive.daymotion.presentation.start

import androidx.lifecycle.LiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import org.positive.daymotion.data.pref.AppSharedPreference
import org.positive.daymotion.data.repository.RemoteConfigRepository
import org.positive.daymotion.presentation.common.SingleLiveEvent
import org.positive.daymotion.presentation.common.base.BaseViewModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val appSharedPreference: AppSharedPreference,
    private val remoteConfigRepository: RemoteConfigRepository
) : BaseViewModel() {

    private val _needUpdate = SingleLiveEvent<Nothing>()
    val needUpdate: LiveData<Nothing> get() = _needUpdate

    private val _goToLogin = SingleLiveEvent<Nothing>()
    val goToLogin: LiveData<Nothing> get() = _goToLogin

    private val _goToHome = SingleLiveEvent<Nothing>()
    val goToHome: LiveData<Nothing> get() = _goToHome

    fun checkVersionAndToken(currentVersion: String) {
        remoteConfigRepository.fetchRemoteData()
            .andThen(Single.just(remoteConfigRepository.getForceUpdateVersion()))
            .delay(500, TimeUnit.MILLISECONDS)
            .map { forceUpdateVersion ->
                val currentVersionValues = currentVersion.split(".").map { it.toInt() }
                val forceUpdateVersionValues = forceUpdateVersion.split(".").map { it.toInt() }
                require(currentVersionValues.size == 3 && forceUpdateVersionValues.size == 3) {
                    "Invalid version format"
                }
                currentVersionValues to forceUpdateVersionValues
            }
            .backgroundCompose()
            .autoDispose {
                success { (current, force) ->
                    if (checkForceUpdate(current, force)) {
                        _needUpdate.call()
                    } else {
                        checkIssuedToken()
                    }
                }
                error { showErrorMessage(it.message.orEmpty()) }
            }
    }

    private fun checkForceUpdate(
        currentVersionValues: List<Int>,
        versionValues: List<Int>
    ) = versionValues
        .zip(currentVersionValues)
        .let { it.any { (f, c) -> f > c } || it.all { (f, c) -> f == c } }

    private fun checkIssuedToken() {
        appSharedPreference.loadAuthToken()
            .backgroundCompose()
            .autoDispose {
                success {
                    _goToLogin.call()
                }
                complete {
                    _goToHome.call()
                }
                error { showErrorMessage(it.message.orEmpty()) }
            }
    }
}