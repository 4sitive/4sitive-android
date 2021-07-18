package org.positive.daymotion.presentation.common.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

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

    fun <T> LiveData<T>.observeWithPrevious(onChange: (old: T, new: T) -> Unit) {
        val diffObserver = object : Observer<T> {
            private var previous: T? = null

            override fun onChanged(new: T?) {
                val old = previous
                if (old != null && new != null && old != new) {
                    onChange(old, new)
                }
                previous = new
            }
        }

        this.observe(lifecycleOwner, diffObserver)
    }
}