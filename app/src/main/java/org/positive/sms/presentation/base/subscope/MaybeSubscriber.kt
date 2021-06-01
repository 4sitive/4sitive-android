package org.positive.sms.presentation.base.subscope

import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

interface MaybeSubscriber {

    fun <T> Maybe<T>.autoDispose(block: MaybeSubscribeScope<T>.() -> Unit)

    fun <T> Maybe<T>.backgroundCompose(): Maybe<T>

    fun <T> Maybe<T>.apiLoadingCompose(): Maybe<T>
}

class MaybeSubscriberImpl(
    private val disposables: CompositeDisposable,
    private val _isLoading: MutableLiveData<Boolean>
) : MaybeSubscriber {

    override fun <T> Maybe<T>.autoDispose(block: MaybeSubscribeScope<T>.() -> Unit) {
        val scope = MaybeSubscribeScope(this, disposables)
        scope.block()
        scope.subscribe()
    }

    override fun <T> Maybe<T>.backgroundCompose(): Maybe<T> = compose {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun <T> Maybe<T>.apiLoadingCompose(): Maybe<T> = compose {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _isLoading.value = true }
            .doFinally { _isLoading.value = false }
    }
}
