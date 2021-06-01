package org.positive.sms.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.positive.sms.network.AuthInterceptor
import org.positive.sms.network.OauthInterceptor
import org.positive.sms.network.TokenAuthenticator
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object OkHttpClientModule {

    @Provides
    @Named("oauth")
    fun provideOkHttpClient(
        oauthInterceptor: OauthInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        chuckerInterceptor: ChuckerInterceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(oauthInterceptor)
        .addInterceptor(chuckerInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .build()

    @Provides
    @Named("certified")
    fun provideCertifiedOkHttpClient(
        authInterceptor: AuthInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        chuckerInterceptor: ChuckerInterceptor,
        tokenAuthenticator: TokenAuthenticator
    ) = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(chuckerInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .authenticator(tokenAuthenticator)
        .build()
}