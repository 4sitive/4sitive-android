package org.positive.daymotion.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.positive.daymotion.common.SingleLiveEvent
import org.positive.daymotion.presentation.base.subscriber.*

abstract class BaseViewModel constructor(
    disposables: CompositeDisposable = CompositeDisposable(),
    isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>(),
) : ViewModel(),
    SingleSubscriber by SingleSubscriberImpl(disposables, isLoading),
    MaybeSubscriber by MaybeSubscriberImpl(disposables, isLoading),
    CompletableSubscriber by CompletableSubscriberImpl(disposables, isLoading) {

    val isLoading: LiveData<Boolean> = isLoading

    private val _showErrorMessageEvent: SingleLiveEvent<String> = SingleLiveEvent()
    val showErrorMessageEvent: LiveData<String> = _showErrorMessageEvent

    protected fun showErrorMessage(message: String) {
        _showErrorMessageEvent.value = message
    }
}