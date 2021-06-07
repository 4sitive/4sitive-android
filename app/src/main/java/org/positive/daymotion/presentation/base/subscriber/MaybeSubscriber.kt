package org.positive.daymotion.presentation.base.subscriber

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.schedulers.Schedulers

interface MaybeSubscriber : BaseSubscriber {

    fun <T> Maybe<T>.autoDispose(block: MaybeSubscribeScope<T>.() -> Unit) {
        val scope = MaybeSubscribeScope(this, disposables)
        scope.block()
        scope.subscribe()
    }

    fun <T> Maybe<T>.backgroundCompose(): Maybe<T> = compose {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun <T> Maybe<T>.apiLoadingCompose(): Maybe<T> = compose {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loadingMutableLiveData.value = true }
            .doFinally { loadingMutableLiveData.value = false }
    }
}