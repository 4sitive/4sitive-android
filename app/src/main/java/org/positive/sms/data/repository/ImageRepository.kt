package org.positive.sms.data.repository

import io.reactivex.rxjava3.core.Single
import org.positive.sms.data.model.ImageUploadResponse

interface ImageRepository {

    fun imageUpload(path: String): Single<ImageUploadResponse>
}