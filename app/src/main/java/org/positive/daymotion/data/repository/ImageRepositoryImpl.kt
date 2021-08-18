package org.positive.daymotion.data.repository

import android.content.Context
import android.net.Uri
import io.reactivex.rxjava3.core.Single
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.positive.daymotion.data.api.ImageApi
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ImageRepositoryImpl @Inject constructor(
    private val context: Context,
    private val imageApi: ImageApi
) : ImageRepository {

    override fun imageUpload(path: String): Single<String> {
        val uri = Uri.parse(path)
        val fileName = dateFormat.format(Date())
        return readImageToByteArray(uri).flatMap { byteArray ->
            imageApi.imageUpload(fileName, byteArray.toRequestBody(imagePngMediaType))
        }.map { response ->
            val contentLocation = requireNotNull(response.headers()["Content-Location"])
            contentLocation
        }
    }

    private fun readImageToByteArray(uri: Uri) = Single.create<ByteArray> { emitter ->
        try {
            requireNotNull(context.contentResolver.openInputStream(uri)).use {
                val body = it.readBytes()
                emitter.onSuccess(body)
            }
        } catch (exception: Exception) {
            emitter.onError(exception)
        }
    }

    companion object {
        private val imagePngMediaType: MediaType = "image/*".toMediaType()
        private val dateFormat = SimpleDateFormat("yyyyMMddHHmm'.png'", Locale.KOREA)
    }
}