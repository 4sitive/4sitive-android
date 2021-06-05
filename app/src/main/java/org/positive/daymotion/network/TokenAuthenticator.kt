package org.positive.daymotion.network

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.positive.daymotion.data.pref.AppSharedPreference
import org.positive.daymotion.data.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(
    private val authRepository: AuthRepository,
    private val appSharedPreference: AppSharedPreference
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val retryCnt = (response.request.header(RETRY_COUNT_HEADER) ?: "0").toInt()
        if (retryCnt > MAX_RETRY_COUNT) {
            appSharedPreference.clearAuthToken().blockingAwait()
            return null
        }

        val newToken = appSharedPreference.loadAuthToken()
            .flatMapSingle { savedToken ->
                authRepository.refreshAccessToken(savedToken.refreshToken)
            }
            .flatMapSingle { newToken ->
                appSharedPreference.saveAuthToken(newToken).toSingle { newToken }
            }.blockingGet() ?: return null

        return response.request.newBuilder()
            .header(RETRY_COUNT_HEADER, (retryCnt + 1).toString())
            .removeHeader("Authorization")
            .addHeader("Authorization", "${newToken.tokenType} ${newToken.accessToken}")
            .build()
    }

    companion object {
        private const val RETRY_COUNT_HEADER = "RetryCount"
        private const val MAX_RETRY_COUNT = 2
    }
}