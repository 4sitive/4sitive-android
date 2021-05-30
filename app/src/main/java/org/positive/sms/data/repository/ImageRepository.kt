package org.positive.sms.data.repository

import io.reactivex.rxjava3.core.Single

interface ImageRepository {

    fun imageUpload(path: String): Single<String>
}