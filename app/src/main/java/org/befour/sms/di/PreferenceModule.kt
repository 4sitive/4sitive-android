package org.befour.sms.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.befour.sms.data.pref.AppSharedPreference
import org.befour.sms.data.pref.AppSharedPreferenceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferenceModule {

    @Binds
    abstract fun provideAppSharedPreference(
        appSharedPreferenceImpl: AppSharedPreferenceImpl
    ): AppSharedPreference
}