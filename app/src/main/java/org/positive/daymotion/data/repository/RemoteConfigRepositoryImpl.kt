package org.positive.daymotion.data.repository

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import io.reactivex.rxjava3.core.Completable
import org.positive.daymotion.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteConfigRepositoryImpl @Inject constructor() : RemoteConfigRepository {

    private val remoteConfig by lazy { Firebase.remoteConfig }

    override fun fetchRemoteData(): Completable {
        remoteConfig.apply {
            setConfigSettingsAsync(remoteConfigSettings { minimumFetchIntervalInSeconds = 3600 })
            setDefaultsAsync(R.xml.remote_config_defaults)
        }

        return Completable.create { emitter ->
            remoteConfig.fetchAndActivate()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        emitter.onComplete()
                    } else {
                        emitter.onError(IllegalStateException("RemoteConfig fetch failed"))
                    }
                }
        }
    }

    override fun getForceUpdateVersion(): String =
        remoteConfig[FORCE_UPDATE_VERSION_KEY].asString()

    companion object {
        private const val FORCE_UPDATE_VERSION_KEY = "force_update_version"
    }
}