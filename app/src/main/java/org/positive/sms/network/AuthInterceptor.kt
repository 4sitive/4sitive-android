package org.positive.sms.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.positive.sms.data.pref.AppSharedPreference
import org.positive.sms.domain.AuthToken
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val appSharedPreference: AppSharedPreference
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val authToken: AuthToken? = appSharedPreference.loadAuthToken().blockingGet()
        val request: Request = chain.request()
        val authenticatedRequest: Request = request
            .newBuilder()
            .addHeader("Authorization", "${authToken?.tokenType} ${authToken?.accessToken}")
            .build()

        return chain.proceed(authenticatedRequest)
    }
}