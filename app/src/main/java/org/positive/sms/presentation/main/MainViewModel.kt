package org.positive.sms.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.sms.data.repository.ImageRepository
import org.positive.sms.presentation.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val imageRepository: ImageRepository
) : BaseViewModel() {

    private val _image = MutableLiveData<String>()
    val image: LiveData<String> get() = _image

    fun upload(path: String) {
        imageRepository.imageUpload(path)
            .apiLoadingCompose()
            .autoDispose {
                success {
                    _image.value = it.contentsLocation
                }
                error {
                    showErrorMessage(it.message.orEmpty())
                }
            }
    }
}