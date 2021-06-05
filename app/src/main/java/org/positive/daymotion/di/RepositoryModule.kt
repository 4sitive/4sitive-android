package org.positive.daymotion.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.positive.daymotion.data.repository.AuthRepository
import org.positive.daymotion.data.repository.AuthRepositoryImpl
import org.positive.daymotion.data.repository.ImageRepository
import org.positive.daymotion.data.repository.ImageRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun provideImageRepository(
        imageRepositoryImpl: ImageRepositoryImpl
    ): ImageRepository
}