package org.positive.sms.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.positive.sms.data.api.AuthApi
import org.positive.sms.data.api.ImageApi
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideAuthApi(@Named("account") retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Provides
    fun provideImageApi(@Named("cdn") retrofit: Retrofit): ImageApi =
        retrofit.create(ImageApi::class.java)
}