package org.positive.sms.presentation.dispatch

import androidx.lifecycle.LiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.sms.common.PsConstants
import org.positive.sms.common.SingleLiveEvent
import org.positive.sms.data.pref.AppSharedPreference
import org.positive.sms.data.repository.AuthRepository
import org.positive.sms.presentation.base.BaseViewModel
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
            redirectUri = PsConstants.APP_SCHEME + "://login"
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