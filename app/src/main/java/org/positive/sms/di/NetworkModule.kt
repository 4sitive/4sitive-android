package org.positive.sms.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.positive.sms.data.api.AuthApi
import org.positive.sms.data.api.ServerTimeApi
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val SERVER_TIME_BASE_URL = "https://worldtimeapi.org/"
    private const val ACCOUNT_SERVER_BASE_URL = "https://account.4sitive.com/"

    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .addInterceptor(httpLoggingInterceptor)
        .build()

    @Provides
    fun provideCallAdapterFactory(): CallAdapter.Factory = RxJava3CallAdapterFactory.create()

    @Provides
    fun provideServerTimeRetrofit(
        okHttpClient: OkHttpClient,
        callAdapterFactory: CallAdapter.Factory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(SERVER_TIME_BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(callAdapterFactory)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Named("account")
    fun provideAccountServerRetrofit(
        okHttpClient: OkHttpClient,
        callAdapterFactory: CallAdapter.Factory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(ACCOUNT_SERVER_BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(callAdapterFactory)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideServerTimeApi(retrofit: Retrofit): ServerTimeApi =
        retrofit.create(ServerTimeApi::class.java)

    @Provides
    fun provideAuthApi(@Named("account") retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    class AuthInterceptor : Interceptor {

        private val credentials: String =
            Credentials.basic("4sitive", "secret")

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
}