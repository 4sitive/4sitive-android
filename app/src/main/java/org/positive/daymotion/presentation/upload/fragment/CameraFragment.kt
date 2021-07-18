package org.positive.daymotion.presentation.upload.fragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.positive.daymotion.R
import org.positive.daymotion.databinding.FragmentCameraBinding
import org.positive.daymotion.presentation.common.base.BaseFragment
import org.positive.daymotion.presentation.common.showPopupDialog
import org.positive.daymotion.presentation.upload.CameraManager

class CameraFragment : BaseFragment<FragmentCameraBinding>(R.layout.fragment_camera) {

    private lateinit var cameraProvider: ProcessCameraProvider
    private lateinit var currentCameraManager: CameraManager
    private var eventListener: EventListener? = null

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (!it.all { (_, v) -> v }) {
                showRequirePermissionPopup()
            }
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is EventListener) {
            eventListener = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpCamera()
    }

    override fun onStart() {
        super.onStart()
        checkPermission()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if (hidden) {
            currentCameraManager.shutDown()
            cameraProvider.unbindAll()
            eventListener?.onCameraStateChange(
                isAvailableCamera = false,
                isAvailableToggle = false
            )
        } else {
            setUpCamera()
        }
    }

    private fun setUpCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        val mainExecutors = ContextCompat.getMainExecutor(requireContext())
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            setupCameraManager(cameraProvider)
            eventListener?.onCameraStateChange(
                isAvailableCamera = true,
                isAvailableToggle = currentCameraManager.isAvailableToggle
            )
        }, mainExecutors)
    }

    private fun setupCameraManager(cameraProvider: ProcessCameraProvider) {
        currentCameraManager = CameraManager(
            cameraProvider,
            requireContext(),
            { eventListener?.onImageSaved(it) },
            { showImageCaptureErrorPopup(it) }
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

    private fun checkPermission() {
        val context = requireContext()
        var needPermissionRequest = false

        PERMISSIONS_REQUIRED.forEach {
            val isGranted =
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            if (!isGranted && shouldShowRequestPermissionRationale(it)) {
                showRequirePermissionPopup()
                return
            }
            if (!isGranted) {
                needPermissionRequest = true
            }
        }

        if (needPermissionRequest) {
            requestPermissionLauncher.launch(PERMISSIONS_REQUIRED)
        }
    }

    private fun showRequirePermissionPopup() {
        showPopupDialog {
            title = "권한이 필요합니다."
            content = "위치, 저장공간, 카메라에 대한 권한이 없으면\n글을 작성할 수 없습니다."
            blueButtonText = "확인"
            isCancelable = false
            isVisibleGrayButton = false
            onClickBlueButton { activity?.finish() }
        }
    }

    private fun showImageCaptureErrorPopup(throwable: Throwable) {
        showPopupDialog {
            title = "사진 촬영 중 알 수 없는 문제가 발생하였습니다."
            content = throwable.message.orEmpty()
            blueButtonText = "확인"
            isCancelable = false
            isVisibleGrayButton = false
            onClickBlueButton { activity?.finish() }
        }
    }

    private fun startFlashAnimation() {
        lifecycleScope.launch {
            val color = ContextCompat.getColor(requireContext(), R.color._000000)
            binding.cameraContainer.foreground = ColorDrawable(color)
            delay(100)
            binding.cameraContainer.foreground = null
        }
    }

    interface EventListener {
        fun onCameraStateChange(isAvailableCamera: Boolean, isAvailableToggle: Boolean)
        fun onImageSaved(uri: Uri)
    }

    companion object {
        private val PERMISSIONS_REQUIRED = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }
}