package org.positive.sms.presentation.start

import androidx.lifecycle.LiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Maybe
import org.positive.sms.common.SingleLiveEvent
import org.positive.sms.data.pref.AppSharedPreference
import org.positive.sms.presentation.base.BaseViewModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val appSharedPreference: AppSharedPreference
) : BaseViewModel() {

    private val _alreadyTokenIssued = SingleLiveEvent<Boolean>()
    val alreadyTokenIssued: LiveData<Boolean> get() = _alreadyTokenIssued

    fun checkIssuedToken() {
        Maybe.zip(
            appSharedPreference.loadAuthToken(),
            Maybe.timer(1500, TimeUnit.MILLISECONDS),
            { authToken, _ -> authToken }
        ).backgroundCompose()
            .autoDispose {
                success { _alreadyTokenIssued.value = true }
                complete { _alreadyTokenIssued.value = false }
                error { showErrorMessage(it.message.orEmpty()) }
            }
    }
}