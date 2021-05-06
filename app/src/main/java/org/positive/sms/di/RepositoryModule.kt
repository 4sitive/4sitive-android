package org.positive.sms.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.positive.sms.data.repository.ServerTimeRepository
import org.positive.sms.data.repository.ServerTimeRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideServerTimeRepository(
        serverTimeRepositoryImpl: ServerTimeRepositoryImpl
    ): ServerTimeRepository
}