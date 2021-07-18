package org.positive.daymotion.presentation.upload

import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Handler
import android.webkit.MimeTypeMap
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.net.toFile
import androidx.lifecycle.LifecycleOwner
import org.positive.daymotion.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraManager(
    cameraProvider: ProcessCameraProvider,
    private val context: Context,
    private val onCaptureComplete: (Uri) -> Unit = {},
    private val onCaptureFailed: (ImageCaptureException) -> Unit = {}
) {
    private val outputDir = getOrCreateOutputDir(context)
    private val preview: Preview
    private val imageCapture: ImageCapture
    private val cameraExecutor: ExecutorService
    private val handler = Handler(context.mainLooper)
    private var lensFacing: Int
    val isAvailableToggle: Boolean

    init {
        preview = buildPreview()
        lensFacing = getAvailableLensFacing(cameraProvider)
        imageCapture = buildImageCapture()
        cameraExecutor = Executors.newSingleThreadExecutor()

        isAvailableToggle = cameraProvider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA) &&
                cameraProvider.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA)
    }

    fun bindToLifecycle(
        lifecycleOwner: LifecycleOwner,
        cameraProvider: ProcessCameraProvider
    ) {
        val cameraSelector = buildCameraSelector(lensFacing)
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageCapture)
    }

    fun reBindToLifecycleWithToggling(
        lifecycleOwner: LifecycleOwner,
        cameraProvider: ProcessCameraProvider
    ) {
        toggleLensFacing()
        val cameraSelector = buildCameraSelector(lensFacing)
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageCapture)
    }

    fun setSurfaceProvider(surfaceProvider: Preview.SurfaceProvider) {
        preview.setSurfaceProvider(surfaceProvider)
    }

    fun takePicture() {
        val photoFile = createFile()
        val metadata = ImageCapture.Metadata().apply {
            isReversedHorizontal = lensFacing == CameraSelector.LENS_FACING_FRONT
        }

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile)
            .setMetadata(metadata)
            .build()

        imageCapture.takePicture(
            outputOptions,
            cameraExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = outputFileResults.savedUri ?: Uri.fromFile(photoFile)
                    val mimeType = MimeTypeMap.getSingleton()
                        .getMimeTypeFromExtension(savedUri.toFile().extension)
                    MediaScannerConnection.scanFile(
                        context, arrayOf(savedUri.toFile().absolutePath), arrayOf(mimeType)
                    ) { _, uri ->
                        handler.post { onCaptureComplete(uri) }
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    onCaptureFailed(exception)
                }
            })
    }

    fun shutDown() {
        cameraExecutor.shutdown()
    }

    private fun getOrCreateOutputDir(context: Context): File {
        val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
            File(it, context.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else context.filesDir
    }

    private fun buildPreview() = Preview.Builder()
        .build()

    private fun buildImageCapture() = ImageCapture.Builder()
        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
        .build()

    private fun buildCameraSelector(lensFacing: Int) = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    private fun getAvailableLensFacing(
        cameraProvider: ProcessCameraProvider
    ) = when {
        cameraProvider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA) -> CameraSelector.LENS_FACING_BACK
        cameraProvider.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA) -> CameraSelector.LENS_FACING_FRONT
        else -> throw IllegalStateException("사용가능한 카메라가 존재하지 않습니다.")
    }

    private fun toggleLensFacing() {
        if (lensFacing == CameraSelector.LENS_FACING_BACK) {
            lensFacing = CameraSelector.LENS_FACING_FRONT
        } else if (lensFacing == CameraSelector.LENS_FACING_FRONT) {
            lensFacing = CameraSelector.LENS_FACING_BACK
        }
    }

    private fun createFile() = File(
        outputDir,
        fileNameFormat.format(System.currentTimeMillis()) + PHOTO_EXTENSION
    )

    companion object {
        private const val PHOTO_EXTENSION = ".jpg"
        private val fileNameFormat = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.KOREA)
    }
}