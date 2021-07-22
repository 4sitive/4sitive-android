package org.positive.daymotion.presentation.common.util

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

@MainThread
fun <X, Y, Z> liveDataMerge(
    source1: LiveData<X>,
    source2: LiveData<Y>,
    mergeFunction: (X, Y) -> Z
): LiveData<Z> = MediatorLiveData<Z>().apply {
    addSource(source1) { x ->
        val source2Value = source2.value
        if (source2Value != null) {
            value = mergeFunction(x, source2Value)
        }
    }

    addSource(source2) { y ->
        val source1Value = source1.value
        if (source1Value != null) {
            value = mergeFunction(source1Value, y)
        }
    }
}

@MainThread
fun <I1, I2, I3, O> liveDataMerge(
    source1: LiveData<I1>,
    source2: LiveData<I2>,
    source3: LiveData<I3>,
    mergeFunction: (I1, I2, I3) -> O
): LiveData<O> = MediatorLiveData<O>().apply {
    addSource(source1) { x ->
        val source2Value = source2.value
        val source3Value = source3.value
        if (source2Value != null && source3Value != null) {
            value = mergeFunction(x, source2Value, source3Value)
        }
    }

    addSource(source2) { y ->
        val source1Value = source1.value
        val source3Value = source3.value
        if (source1Value != null && source3Value != null) {
            value = mergeFunction(source1Value, y, source3Value)
        }
    }

    addSource(source3) { z ->
        val source1Value = source1.value
        val source2Value = source2.value
        if (source1Value != null && source2Value != null) {
            value = mergeFunction(source1Value, source2Value, z)
        }
    }
}