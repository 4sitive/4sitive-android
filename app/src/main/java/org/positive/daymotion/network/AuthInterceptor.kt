package org.positive.daymotion.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.positive.daymotion.data.pref.AppSharedPreference
import org.positive.daymotion.domain.AuthToken
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val appSharedPreference: AppSharedPreference
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val authToken: AuthToken? = appSharedPreference.loadAuthToken().blockingGet()
        val request: Request = chain.request()
        val authenticatedRequest: Request = request
            .newBuilder()
            .addHeader("Authorization", "Bearer ${authToken?.accessToken}")
            .build()

        return chain.proceed(authenticatedRequest)
    }
}