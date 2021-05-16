package org.positive.sms.presentation.login

import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.sms.data.model.GrantType
import org.positive.sms.data.model.PostOauthAuthorizationCodeRequest
import org.positive.sms.data.pref.AppSharedPreference
import org.positive.sms.data.repository.AuthRepository
import org.positive.sms.presentation.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sharedPreferences: AppSharedPreference
) : BaseViewModel() {

    fun postAuthorizationCode(code: String) {
        authRepository.postOauthAuthorizationCode(
            code = code,
            grantType = GrantType.AUTHORIZATION_CODE,
            redirectUri = "http://127.0.0.1:8080/authorized"
        )
            .compose(apiLoading())
            .autoDispose {
                success {
                    sharedPreferences.authToken = it.accessToken
                }
                error {
                    showErrorMessage(it.message.orEmpty())
                }
            }
    }
}