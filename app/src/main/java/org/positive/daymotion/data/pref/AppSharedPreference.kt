package org.positive.daymotion.data.pref

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import org.positive.daymotion.domain.AuthToken

interface AppSharedPreference {

    fun loadAuthToken(): Maybe<AuthToken>

    fun saveAuthToken(authToken: AuthToken): Completable

    fun clearAuthToken(): Completable
}