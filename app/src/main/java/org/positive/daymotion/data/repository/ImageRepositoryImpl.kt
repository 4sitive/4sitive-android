package org.positive.daymotion.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import io.reactivex.rxjava3.core.Single
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.positive.daymotion.DmConstants.IMAGE_SERVER_BASE_URL
import org.positive.daymotion.data.api.ImageApi
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ImageRepositoryImpl @Inject constructor(
    private val context: Context,
    private val imageApi: ImageApi
) : ImageRepository {

    override fun imageUpload(path: String): Single<String> =
        loadBitmap(path)
            .flatMap { uri -> bitmapToByteArray(uri) }
            .flatMap { byteArray ->
                val fileName = dateFormat.format(Date())
                imageApi.imageUpload(fileName, byteArray.toRequestBody(imagePngMediaType))
            }.map { response ->
                val contentLocation = response.headers()["Content-Location"]?.let {
                    IMAGE_SERVER_BASE_URL + it
                }.orEmpty()
                contentLocation
            }

    private fun loadBitmap(uriStr: String) = Single.create<Bitmap> { emitter ->
        try {
            val uri = Uri.parse(uriStr)
            val inSampleSize = calcInSampleSize(uri)
            val bitmap = loadBitmapWithSampled(uri, inSampleSize)
            emitter.onSuccess(bitmap)
        } catch (exception: Exception) {
            emitter.onError(exception)
        }
    }

    private fun bitmapToByteArray(bitmap: Bitmap) = Single.create<ByteArray> { emitter ->
        try {
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            emitter.onSuccess(outputStream.toByteArray())
        } catch (exception: Exception) {
            emitter.onError(exception)
        }
    }

    private fun calcInSampleSize(uri: Uri): Int =
        requireNotNull(context.contentResolver.openInputStream(uri)).use {
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            BitmapFactory.decodeStream(it, null, options)
            val height: Int = options.outHeight
            val width: Int = options.outWidth

            val longSide = if (height > width) height else width

            var inSampleSize = 1
            var sampledSize = longSide

            while (sampledSize > LIMIT_IMAGE_SIZE) {
                sampledSize /= 2
                inSampleSize *= 2
            }

            inSampleSize
        }

    private fun loadBitmapWithSampled(uri: Uri, inSampleSize: Int): Bitmap {
        val bitmap = requireNotNull(context.contentResolver.openInputStream(uri)).use {
            val options = BitmapFactory.Options().apply {
                this.inJustDecodeBounds = false
                this.inSampleSize = inSampleSize

            }
            val bitmap = BitmapFactory.decodeStream(it, null, options)

            requireNotNull(bitmap)
        }

        return requireNotNull(context.contentResolver.openInputStream(uri)).use {
            val exif = ExifInterface(it)
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
            val rotate = when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_270 -> 270
                ExifInterface.ORIENTATION_ROTATE_180 -> 180
                ExifInterface.ORIENTATION_ROTATE_90 -> 90
                else -> 0
            }
            val matrix = Matrix()
            matrix.postRotate(rotate.toFloat())
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        }
    }


    companion object {
        private val imagePngMediaType: MediaType = "image/*".toMediaType()
        private val dateFormat = SimpleDateFormat("yyyyMMddHHmm'.png'", Locale.KOREA)
        private const val LIMIT_IMAGE_SIZE = 1000
    }
}