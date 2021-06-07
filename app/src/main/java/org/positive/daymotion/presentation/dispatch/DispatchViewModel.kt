package org.positive.daymotion.presentation.dispatch

import androidx.lifecycle.LiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.common.DmConstants
import org.positive.daymotion.common.SingleLiveEvent
import org.positive.daymotion.data.pref.AppSharedPreference
import org.positive.daymotion.data.repository.AuthRepository
import org.positive.daymotion.presentation.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class DispatchViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sharedPreferences: AppSharedPreference
) : BaseViewModel() {

    private val _tokenIssueCompleteEvent = SingleLiveEvent<Nothing>()
    val tokenIssueCompleteEvent: LiveData<Nothing> get() = _tokenIssueCompleteEvent

    fun postAuthorizationCode(code: String) {
        authRepository.postOauthAuthorizationCode(
            code = code,
            redirectUri = DmConstants.APP_SCHEME + "://login"
        ).flatMapCompletable { sharedPreferences.saveAuthToken(it) }
            .apiLoadingCompose()
            .autoDispose {
                complete {
                    _tokenIssueCompleteEvent.call()
                }
                error {
                    showErrorMessage(it.message.orEmpty())
                }
            }
    }
}