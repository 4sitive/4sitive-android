package org.positive.sms.common

import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MaybeSubscribeScope<T>(
    private val source: Maybe<T>,
    private val disposables: CompositeDisposable
) {
    private var onSuccess: (T) -> Unit = {}
    private var onError: (Throwable) -> Unit = {}
    private var onComplete: () -> Unit = {}

    fun success(onSuccess: (T) -> Unit) {
        this.onSuccess = onSuccess
    }

    fun error(onError: (Throwable) -> Unit) {
        this.onError = onError
    }

    fun complete(onComplete: () -> Unit) {
        this.onComplete = onComplete
    }

    fun subscribe() {
        disposables.add(source.subscribe(onSuccess, onError, onComplete))
    }
}