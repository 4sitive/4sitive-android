package org.positive.daymotion.presentation.upload.fragment

import android.os.Bundle
import android.view.View
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import org.positive.daymotion.R
import org.positive.daymotion.databinding.FragmentCameraBinding
import org.positive.daymotion.presentation.common.base.BaseFragment
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraFragment : BaseFragment<FragmentCameraBinding>(R.layout.fragment_camera) {

    private lateinit var cameraExecutor: ExecutorService
    private lateinit var imageCapture: ImageCapture

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpCamera()
    }

    private fun setUpCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val lensFacing = when {
                cameraProvider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA) -> CameraSelector.LENS_FACING_BACK
                cameraProvider.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA) -> CameraSelector.LENS_FACING_FRONT
                else -> throw IllegalStateException("Back and front camera are unavailable")
            }

            val preview = Preview.Builder()
                .build()

            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(lensFacing)
                .build()

            cameraProvider.unbindAll()

            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()

            cameraExecutor = Executors.newSingleThreadExecutor()

            try {
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
                preview.setSurfaceProvider(binding.previewView.surfaceProvider)
            } catch (e: Exception) {
                showErrorMessage("카메라 준비 단계에서 오류가 발생 했습니다.")
            }

            cameraProvider.availableCameraInfos

        }, ContextCompat.getMainExecutor(requireContext()))
    }
}