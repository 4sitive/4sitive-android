package org.positive.daymotion.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import org.positive.daymotion.BuildConfig

@Module
@InstallIn(SingletonComponent::class)
object InterceptorModule {

    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun provideChuckerInterceptor(context: Context) = ChuckerInterceptor.Builder(context)
        .collector(ChuckerCollector(context, true))
        .build()
}