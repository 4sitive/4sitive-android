package org.positive.sms.data.repository

import android.content.Context
import android.net.Uri
import io.reactivex.rxjava3.core.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.positive.sms.data.api.ImageApi
import javax.inject.Inject


class ImageRepositoryImpl @Inject constructor(
    private val context: Context,
    private val imageApi: ImageApi
) : ImageRepository {

    override fun imageUpload(path: String): Single<String> {
        val inputStream = context.contentResolver.openInputStream(Uri.parse(path))!!
        val buf = inputStream.readBytes()
        inputStream.close()
        val requestBody = buf.toRequestBody("image/png".toMediaTypeOrNull())
        return imageApi.imageUpload("from_and.txt", requestBody)
            .map { it.headers().toString() }
    }
}