package org.positive.sms.data.pref

import android.content.Context
import androidx.datastore.rxjava3.RxDataStore
import androidx.datastore.rxjava3.RxDataStoreBuilder
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import org.positive.sms.BuildConfig
import org.positive.sms.datastore.AuthTokenEntity
import org.positive.sms.domain.AuthToken
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSharedPreferenceImpl @Inject constructor(
    context: Context
) : AppSharedPreference {

    private val dataStore: RxDataStore<AuthTokenEntity> = RxDataStoreBuilder(
        context,
        AUTH_TOKEN_DATA_STORE_NAME,
        AuthTokenEntitySerializer()
    ).build()

    override fun loadAuthToken(): Maybe<AuthToken> =
        dataStore.data().firstElement().map { it.toAuthToken() }

    override fun saveAuthToken(authToken: AuthToken): Completable =
        dataStore.updateDataAsync {
            val authTokenEntity = AuthTokenEntity.newBuilder()
                .setAccessToken(authToken.accessToken)
                .setRefreshToken(authToken.refreshToken)
                .setExpiresIn(authToken.expiresIn)
                .addAllScope(authToken.scope.orEmpty())
                .build()
            Single.just(authTokenEntity)
        }.ignoreElement()

    private fun AuthTokenEntity.toAuthToken(): AuthToken = AuthToken(
        accessToken = accessToken,
        refreshToken = refreshToken,
        expiresIn = expiresIn,
        tokenType = tokenType,
        scope = scopeList
    )

    companion object {

        private const val AUTH_TOKEN_DATA_STORE_NAME = BuildConfig.APPLICATION_ID + "_data_store"
    }
}