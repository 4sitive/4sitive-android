package org.positive.sms.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleTransformer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.positive.sms.common.SingleLiveEvent
import org.positive.sms.common.SingleSubscribeScope

abstract class BaseViewModel : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _showErrorMessageEvent = SingleLiveEvent<String>()
    val showErrorMessageEvent: LiveData<String> = _showErrorMessageEvent

    protected fun showErrorMessage(message: String) {
        _showErrorMessageEvent.setValue(message)
    }

    protected fun <T : Any> Single<T>.autoDispose(block: SingleSubscribeScope<T>.() -> Unit) {
        val scope = SingleSubscribeScope(this, disposables)
        scope.block()
        scope.subscribe()
    }

    protected fun <T> apiLoading() = SingleTransformer<T, T> {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _isLoading.value = true }
            .doFinally { _isLoading.value = false }
    }
}