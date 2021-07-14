package org.positive.daymotion.presentation.upload.activity

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityFeedUploadBinding
import org.positive.daymotion.presentation.common.base.BaseActivity
import org.positive.daymotion.presentation.common.base.viewModelOf
import org.positive.daymotion.presentation.common.extension.startWith
import org.positive.daymotion.presentation.common.util.recursiveFileSearch
import org.positive.daymotion.presentation.upload.FeedUploadViewModel
import org.positive.daymotion.presentation.upload.fragment.CameraFragment
import java.util.*

@AndroidEntryPoint
class FeedUploadActivity :
    BaseActivity<ActivityFeedUploadBinding>(R.layout.activity_feed_upload),
    CameraFragment.EventListener {

    private val viewModel by viewModelOf<FeedUploadViewModel>()
    private val handler = Handler()

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        it?.let {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.handler = handler

        supportFragmentManager.commit {
            add(R.id.container, CameraFragment::class.java, null)
        }

        setupViews()
    }

    override fun onReadyCamera(isAvailableToggle: Boolean) {
        viewModel.setReadyCamera()
        if (isAvailableToggle) {
            viewModel.setToggleAvailable()
        }
    }

    override fun onImageSaved(uri: Uri) {

    }

    private fun setupViews() {
        loadGalleryThumbnail()
    }

    private fun loadGalleryThumbnail() {
        lifecycleScope.launch {
            val file = withContext(Dispatchers.IO) {
                val galleryDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                return@withContext recursiveFileSearch(galleryDir) { file ->
                    EXTENSION_WHITELIST.contains(file.extension.toUpperCase(Locale.getDefault()))
                }.maxByOrNull { it.lastModified() }
            }
            if (file != null) {
                binding.galleryImageView.setImageURI(Uri.fromFile(file))
            }
        }
    }

    inner class Handler {
        fun close() = finish()

        fun startGallery() = galleryLauncher.launch("image/*")

        fun toggleLensFacing() {
            supportFragmentManager.fragments
                .filterIsInstance(CameraFragment::class.java)
                .firstOrNull()
                ?.toggleLens()
        }

        fun takePicture() {
            supportFragmentManager.fragments
                .filterIsInstance(CameraFragment::class.java)
                .firstOrNull()
                ?.capture()
        }
    }

    companion object {
        fun start(context: Context) = context.startWith<FeedUploadActivity>()

        private val EXTENSION_WHITELIST = arrayOf("PNG", "JPG")
    }
}