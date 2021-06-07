package org.positive.daymotion.presentation.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

interface LiveDataObservable {

    val lifecycleOwner: LifecycleOwner

    fun <T> LiveData<T>.observe(onChange: (T?) -> Unit) {
        this.observe(lifecycleOwner, onChange)
    }

    fun <T> LiveData<T>.observeNonNull(onChange: (T) -> Unit) {
        this.observe(lifecycleOwner) {
            it?.let { onChange(it) }
        }
    }
}