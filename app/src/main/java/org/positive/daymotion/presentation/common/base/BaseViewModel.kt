package org.positive.daymotion.presentation.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.positive.daymotion.presentation.common.SingleLiveEvent
import org.positive.daymotion.presentation.common.base.subscriber.CompletableSubscriber
import org.positive.daymotion.presentation.common.base.subscriber.MaybeSubscriber
import org.positive.daymotion.presentation.common.base.subscriber.SingleSubscriber

abstract class BaseViewModel : ViewModel(),
    SingleSubscriber, MaybeSubscriber, CompletableSubscriber {

    private val _disposables = CompositeDisposable()

    private val _loadingCount = MutableLiveData(0)
    val loadingCount: LiveData<Int> = _loadingCount

    private val _showErrorMessageEvent: SingleLiveEvent<String> = SingleLiveEvent()
    val showErrorMessageEvent: LiveData<String> = _showErrorMessageEvent

    override val disposables: CompositeDisposable
        get() = _disposables

    override val loadingCountMutableLiveData: MutableLiveData<Int>
        get() = _loadingCount

    protected fun showErrorMessage(message: String) {
        _showErrorMessageEvent.value = message
    }
}