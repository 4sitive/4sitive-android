package org.positive.daymotion.presentation.common.base.subscriber

import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable

interface BaseSubscriber {

    val disposables: CompositeDisposable

    val loadingCountMutableLiveData: MutableLiveData<Int>
}