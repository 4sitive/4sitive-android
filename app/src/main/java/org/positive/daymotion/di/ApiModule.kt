package org.positive.daymotion.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.positive.daymotion.data.api.AuthApi
import org.positive.daymotion.data.api.ImageApi
import org.positive.daymotion.data.api.UserApi
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

    @Provides
    fun provideUserApi(@Named("api") retrofit: Retrofit): UserApi =
        retrofit.create(UserApi::class.java)
}