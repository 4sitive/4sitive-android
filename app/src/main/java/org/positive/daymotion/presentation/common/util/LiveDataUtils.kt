package org.positive.daymotion.presentation.common.util

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

@MainThread
fun <X, Y, Z> liveDataMerge(
    source1: LiveData<X>,
    source2: LiveData<Y>,
    mergeFunction: (X?, Y?) -> Z
): LiveData<Z> = MediatorLiveData<Z>().apply {
    addSource(source1) { x ->
        value = mergeFunction(x, source2.value)
    }

    addSource(source2) { y ->
        value = mergeFunction(source1.value, y)
    }
}