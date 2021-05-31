package org.positive.sms.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.positive.sms.common.CompletableSubscribeScope
import org.positive.sms.common.MaybeSubscribeScope
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

    protected fun <T : Any> Maybe<T>.autoDispose(block: MaybeSubscribeScope<T>.() -> Unit) {
        val scope = MaybeSubscribeScope(this, disposables)
        scope.block()
        scope.subscribe()
    }

    protected fun Completable.autoDispose(block: CompletableSubscribeScope.() -> Unit) {
        val scope = CompletableSubscribeScope(this, disposables)
        scope.block()
        scope.subscribe()
    }

    protected fun <T> Single<T>.apiLoading() = compose {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _isLoading.value = true }
            .doFinally { _isLoading.value = false }
    }

    protected fun <T> Maybe<T>.apiLoading() = compose {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _isLoading.value = true }
            .doFinally { _isLoading.value = false }
    }

    protected fun Completable.apiLoading() = compose {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _isLoading.value = true }
            .doFinally { _isLoading.value = false }
    }
}