package org.positive.daymotion.presentation.upload

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraManager(cameraProvider: ProcessCameraProvider) {
    private val preview: Preview
    private val imageCapture: ImageCapture
    private val cameraExecutor: ExecutorService
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
}