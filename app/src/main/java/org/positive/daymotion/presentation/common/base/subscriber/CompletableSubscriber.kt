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
            .doOnSubscribe {
                val value = loadingCountMutableLiveData.value ?: return@doOnSubscribe
                loadingCountMutableLiveData.value = value + 1
            }
            .doFinally {
                val value = loadingCountMutableLiveData.value ?: return@doFinally
                loadingCountMutableLiveData.value = value - 1
            }
    }
}