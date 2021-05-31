package org.positive.sms.presentation.base.subscope

import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

interface CompletableSubscriber {

    fun Completable.autoDispose(block: CompletableSubscribeScope.() -> Unit)

    fun Completable.apiLoadingCompose(): Completable
}

class CompletableSubscriberImpl(
    private val disposables: CompositeDisposable,
    private val _isLoading: MutableLiveData<Boolean>
) : CompletableSubscriber {

    override fun Completable.autoDispose(block: CompletableSubscribeScope.() -> Unit) {
        val scope = CompletableSubscribeScope(this, disposables)
        scope.block()
        scope.subscribe()
    }

    override fun Completable.apiLoadingCompose() : Completable = compose {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _isLoading.value = true }
            .doFinally { _isLoading.value = false }
    }
}