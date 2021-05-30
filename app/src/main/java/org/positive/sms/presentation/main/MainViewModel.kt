package org.positive.sms.presentation.main

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.sms.data.pref.AppSharedPreference
import org.positive.sms.data.repository.ImageRepository
import org.positive.sms.presentation.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val imageRepository: ImageRepository,
    private val appSharedPreference: AppSharedPreference
) : BaseViewModel() {

    fun upload(path: String) {
        imageRepository.imageUpload(path).compose(apiLoading())
            .autoDispose {
                success {
                    Log.i("image upload", it)
                }
                error {
                    showErrorMessage(it.message.orEmpty())
                }
            }
    }
}