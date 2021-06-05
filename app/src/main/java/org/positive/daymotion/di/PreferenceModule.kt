package org.positive.daymotion.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.positive.daymotion.data.pref.AppSharedPreference
import org.positive.daymotion.data.pref.AppSharedPreferenceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferenceModule {

    @Binds
    abstract fun provideAppSharedPreference(
        appSharedPreferenceImpl: AppSharedPreferenceImpl
    ): AppSharedPreference
}