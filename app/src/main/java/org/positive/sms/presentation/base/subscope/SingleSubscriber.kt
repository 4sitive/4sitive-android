package org.positive.sms.presentation.base.subscope

import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

interface SingleSubscriber {

    fun <T> Single<T>.autoDispose(block: SingleSubscribeScope<T>.() -> Unit)

    fun <T> Single<T>.apiLoadingCompose(): Single<T>
}

class SingleSubscriberImpl(
    private val disposables: CompositeDisposable,
    private val _isLoading: MutableLiveData<Boolean>
) : SingleSubscriber {

    override fun <T> Single<T>.autoDispose(block: SingleSubscribeScope<T>.() -> Unit) {
        val scope = SingleSubscribeScope(this, disposables)
        scope.block()
        scope.subscribe()
    }

    override fun <T> Single<T>.apiLoadingCompose(): Single<T> = compose {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _isLoading.value = true }
            .doFinally { _isLoading.value = false }
    }
}