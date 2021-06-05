package org.positive.daymotion.presentation.base.subscope

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable

class SingleSubscribeScope<T>(
    private val source: Single<T>,
    private val disposables: CompositeDisposable
) {
    private var onSuccess: (T) -> Unit = {}
    private var onError: (Throwable) -> Unit = {}

    fun success(onSuccess: (T) -> Unit) {
        this.onSuccess = onSuccess
    }

    fun error(onError: (Throwable) -> Unit) {
        this.onError = onError
    }

    fun subscribe() {
        disposables.add(source.subscribe(onSuccess, onError))
    }
}