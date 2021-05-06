package org.positive.sms.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.sms.data.pref.AppSharedPreference
import org.positive.sms.data.repository.ServerTimeRepository
import org.positive.sms.presentation.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val serverTimeRepository: ServerTimeRepository,
    private val appSharedPreference: AppSharedPreference
) : BaseViewModel() {

    private val _serverTime = MutableLiveData<Long>()
    val serverTime: LiveData<Long> get() = _serverTime

    private val _localTime = MutableLiveData<Long>()
    val localTime: LiveData<Long> get() = _localTime

    fun updateServerTime() {
        serverTimeRepository.getSeoulTime()
            .compose(apiLoading())
            .autoDispose {
                success {
                    _serverTime.value = it.unixTime
                }
                error {
                    showErrorMessage(it.message.orEmpty())
                }
            }
    }

    fun saveTime() {
        val serverTime = _serverTime.value ?: return
        appSharedPreference.unixTime = serverTime
    }

    fun loadSavedTime() {
        _localTime.value = appSharedPreference.unixTime
    }
}