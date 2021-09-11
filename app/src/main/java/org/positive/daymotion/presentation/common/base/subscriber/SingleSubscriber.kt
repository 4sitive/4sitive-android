package org.positive.daymotion.presentation.common.base.subscriber

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

interface SingleSubscriber : BaseSubscriber {

    fun <T> Single<T>.autoDispose(block: SingleSubscribeScope<T>.() -> Unit) {
        val scope = SingleSubscribeScope(this, disposables)
        scope.block()
        scope.subscribe()
    }

    fun <T> Single<T>.backgroundCompose(): Single<T> = compose {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun <T> Single<T>.apiLoadingCompose(): Single<T> = compose {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loadingLiveData.value = true }
            .doFinally { loadingLiveData.value = false }
    }
}