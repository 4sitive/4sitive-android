package org.positive.daymotion.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.positive.daymotion.network.NullableTypeAdapterFactory
import retrofit2.CallAdapter
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkConverterModule {

    @Provides
    fun provideCallAdapterFactory(): CallAdapter.Factory = RxJava3CallAdapterFactory.create()

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        val gson = GsonBuilder()
            .registerTypeAdapterFactory(NullableTypeAdapterFactory())
            .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
        return GsonConverterFactory.create(gson)
    }
}