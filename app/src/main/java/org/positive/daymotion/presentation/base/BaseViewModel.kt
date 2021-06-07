package org.positive.daymotion.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.positive.daymotion.common.SingleLiveEvent
import org.positive.daymotion.presentation.base.subscriber.CompletableSubscriber
import org.positive.daymotion.presentation.base.subscriber.MaybeSubscriber
import org.positive.daymotion.presentation.base.subscriber.SingleSubscriber

abstract class BaseViewModel : ViewModel(),
    SingleSubscriber, MaybeSubscriber, CompletableSubscriber {

    private val _disposables = CompositeDisposable()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _showErrorMessageEvent: SingleLiveEvent<String> = SingleLiveEvent()
    val showErrorMessageEvent: LiveData<String> = _showErrorMessageEvent

    override val disposables: CompositeDisposable
        get() = _disposables
    override val loadingMutableLiveData: MutableLiveData<Boolean>
        get() = _isLoading

    protected fun showErrorMessage(message: String) {
        _showErrorMessageEvent.value = message
    }
}