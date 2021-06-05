package org.positive.daymotion.data.repository

import io.reactivex.rxjava3.core.Single
import org.positive.daymotion.data.model.ImageUploadResponse

interface ImageRepository {

    fun imageUpload(path: String): Single<ImageUploadResponse>
}