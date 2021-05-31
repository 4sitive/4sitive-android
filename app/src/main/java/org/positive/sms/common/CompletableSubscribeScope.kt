package org.positive.sms.common

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable

class CompletableSubscribeScope(
    private val source: Completable,
    private val disposables: CompositeDisposable
) {
    private var onComplete: () -> Unit = {}
    private var onError: (Throwable) -> Unit = {}

    fun complete(onComplete: () -> Unit) {
        this.onComplete = onComplete
    }

    fun error(onError: (Throwable) -> Unit) {
        this.onError = onError
    }

    fun subscribe() {
        disposables.add(source.subscribe(onComplete, onError))
    }
}