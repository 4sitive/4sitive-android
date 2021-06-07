package org.positive.daymotion

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class DmApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        setupCrashlytics()
        setupRemoteConfig()
    }

    private fun setupCrashlytics() {
        FirebaseCrashlytics
            .getInstance()
            .setCrashlyticsCollectionEnabled(BuildConfig.BUILD_TYPE != "debug")
    }

    private fun setupRemoteConfig() {
        Firebase.remoteConfig.apply {
            setConfigSettingsAsync(remoteConfigSettings { minimumFetchIntervalInSeconds = 3600 })
            setDefaultsAsync(R.xml.remote_config_defaults)
        }
    }
}