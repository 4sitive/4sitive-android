package org.positive.daymotion.data.repository

import io.reactivex.rxjava3.core.Completable

interface RemoteConfigRepository {

    fun fetchRemoteData(): Completable

    fun getForceUpdateVersion(): String
}