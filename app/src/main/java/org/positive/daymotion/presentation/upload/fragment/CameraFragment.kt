package org.positive.daymotion.presentation.upload.fragment

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.positive.daymotion.R
import org.positive.daymotion.databinding.FragmentCameraBinding
import org.positive.daymotion.presentation.common.base.BaseFragment
import org.positive.daymotion.presentation.upload.CameraManager

class CameraFragment : BaseFragment<FragmentCameraBinding>(R.layout.fragment_camera) {

    private lateinit var cameraProvider: ProcessCameraProvider
    private lateinit var currentCameraManager: CameraManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpCamera()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun setUpCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        val mainExecutors = ContextCompat.getMainExecutor(requireContext())
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            setupCameraManager(cameraProvider)
        }, mainExecutors)
    }

    private fun setupCameraManager(cameraProvider: ProcessCameraProvider) {
        currentCameraManager = CameraManager(
            cameraProvider,
            requireContext()
        ).also {
            it.bindToLifecycle(lifecycleOwner, cameraProvider)
            it.setSurfaceProvider(binding.previewView.surfaceProvider)
        }
    }

    fun toggleLens() {
        with(currentCameraManager) {
            reBindToLifecycleWithToggling(lifecycleOwner, cameraProvider)
            setSurfaceProvider(binding.previewView.surfaceProvider)
        }
    }

    fun capture() {
        currentCameraManager.takePicture()
        startFlashAnimation()
    }

    private fun startFlashAnimation() {
        lifecycleScope.launch {
            val color = ContextCompat.getColor(requireContext(), R.color._000000)
            binding.cameraContainer.foreground = ColorDrawable(color)
            delay(100)
            binding.cameraContainer.foreground = null
        }
    }
}