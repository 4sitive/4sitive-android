package org.positive.sms.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.positive.sms.common.SingleLiveEvent
import org.positive.sms.presentation.base.subscope.*

abstract class BaseViewModel constructor(
    private val disposables: CompositeDisposable = CompositeDisposable(),
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>(),
    private val _showErrorMessageEvent: SingleLiveEvent<String> = SingleLiveEvent()
) : ViewModel(),
    SingleSubscriber by SingleSubscriberImpl(disposables, _isLoading),
    MaybeSubscriber by MaybeSubscriberImpl(disposables, _isLoading),
    CompletableSubscriber by CompletableSubscriberImpl(disposables, _isLoading) {

    val isLoading: LiveData<Boolean> = _isLoading
    val showErrorMessageEvent: LiveData<String> = _showErrorMessageEvent

    protected fun showErrorMessage(message: String) {
        _showErrorMessageEvent.value = message
    }
}