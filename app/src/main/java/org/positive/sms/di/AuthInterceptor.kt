package org.positive.sms.di

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.positive.sms.BuildConfig

class AuthInterceptor : Interceptor {

    private val credentials: String =
        Credentials.basic(BuildConfig.OAUTH_CLIENT_ID, BuildConfig.OAUTH_CLIENT_SECRET)

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val authenticatedRequest: Request = request
            .newBuilder()
            .addHeader("Authorization", credentials)
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .build()

        return chain.proceed(authenticatedRequest)
    }
}