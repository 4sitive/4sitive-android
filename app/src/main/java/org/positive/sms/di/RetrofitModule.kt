package org.positive.sms.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.positive.sms.common.PsConstants
import org.positive.sms.data.api.AuthApi
import org.positive.sms.data.api.ImageApi
import org.positive.sms.data.pref.AppSharedPreference
import org.positive.sms.network.AuthInterceptor
import org.positive.sms.network.OauthInterceptor
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Named("account")
    fun provideAccountServerRetrofit(
        okHttpClient: OkHttpClient,
        callAdapterFactory: CallAdapter.Factory,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(PsConstants.ACCOUNT_SERVER_BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(callAdapterFactory)
        .addConverterFactory(gsonConverterFactory)
        .build()

    @Provides
    @Named("cdn")
    fun provideCdnServerRetrofit(
        @Named("certified")
        okHttpClient: OkHttpClient,
        callAdapterFactory: CallAdapter.Factory,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(PsConstants.CDN_SERVER_BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(callAdapterFactory)
        .addConverterFactory(gsonConverterFactory)
        .build()
}

