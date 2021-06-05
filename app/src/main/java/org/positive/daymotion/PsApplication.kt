package org.positive.daymotion

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PsApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseCrashlytics
            .getInstance()
            .setCrashlyticsCollectionEnabled(BuildConfig.BUILD_TYPE != "debug")
    }
}