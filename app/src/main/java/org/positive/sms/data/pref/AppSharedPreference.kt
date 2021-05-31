package org.positive.sms.data.pref

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import org.positive.sms.domain.AuthToken

interface AppSharedPreference {

    fun loadAuthToken(): Maybe<AuthToken>

    fun saveAuthToken(authToken: AuthToken): Completable
}