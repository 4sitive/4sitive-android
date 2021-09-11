package org.positive.daymotion.presentation.common.base.subscriber

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers

interface CompletableSubscriber : BaseSubscriber {

    fun Completable.autoDispose(block: CompletableSubscribeScope.() -> Unit) {
        val scope = CompletableSubscribeScope(this, disposables)
        scope.block()
        scope.subscribe()
    }

    fun Completable.backgroundCompose(): Completable = compose {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun Completable.apiLoadingCompose(): Completable = compose {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loadingLiveData.value = true }
            .doFinally { loadingLiveData.value = false }
    }
}