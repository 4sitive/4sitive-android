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

@MainThread
fun <I1, I2, I3, O> liveDataMerge(
    source1: LiveData<I1>,
    source2: LiveData<I2>,
    source3: LiveData<I3>,
    mergeFunction: (I1?, I2?, I3?) -> O
): LiveData<O> = MediatorLiveData<O>().apply {
    addSource(source1) { x ->
        value = mergeFunction(x, source2.value, source3.value)
    }

    addSource(source2) { y ->
        value = mergeFunction(source1.value, y, source3.value)
    }

    addSource(source3) { z ->
        value = mergeFunction(source1.value, source2.value, z)
    }
}