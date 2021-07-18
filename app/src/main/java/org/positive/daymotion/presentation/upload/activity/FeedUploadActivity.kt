package org.positive.daymotion.presentation.upload.activity

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityFeedUploadBinding
import org.positive.daymotion.presentation.common.base.BaseActivity
import org.positive.daymotion.presentation.common.base.viewModelOf
import org.positive.daymotion.presentation.common.extension.findFragment
import org.positive.daymotion.presentation.common.extension.startWith
import org.positive.daymotion.presentation.common.util.recursiveFileSearch
import org.positive.daymotion.presentation.upload.FeedUploadViewModel
import org.positive.daymotion.presentation.upload.FragmentChangeManager
import org.positive.daymotion.presentation.upload.adapter.BackgroundSelectionAdapter
import org.positive.daymotion.presentation.upload.fragment.CameraFragment
import org.positive.daymotion.presentation.upload.fragment.EditFragment
import org.positive.daymotion.presentation.upload.model.BackgroundSelection
import java.util.*


@AndroidEntryPoint
class FeedUploadActivity :
    BaseActivity<ActivityFeedUploadBinding>(R.layout.activity_feed_upload),
    CameraFragment.EventListener {

    private val viewModel by viewModelOf<FeedUploadViewModel>()
    private val handler = Handler()
    private val backgroundSelectionAdapter = BackgroundSelectionAdapter(handler)
    private val fragmentChangeManager by lazy { FragmentChangeManager(supportFragmentManager) }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let { viewModel.selectCustomImage(BackgroundSelection.Custom(it)) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.handler = handler

        setupFragments()
        setupViews()
        setupObservers()
    }

    override fun onCameraStateChange(isAvailableToggle: Boolean) {
        viewModel.setToggleAvailable(isAvailableToggle)
    }

    override fun onImageSaved(uri: Uri) {
        viewModel.selectCustomImage(BackgroundSelection.Custom(uri))
    }

    private fun setupFragments() {
        supportFragmentManager.commit {
            add(R.id.container, CameraFragment::class.java, null)
            add(R.id.container, EditFragment::class.java, null)
            findFragment<EditFragment>()?.let { hide(it) }
        }
    }

    private fun setupViews() {
        loadGalleryThumbnail()
        setupBackgroundSelectionContainer()
    }

    private fun setupObservers() {
        with(viewModel) {
            selections.observeNonNull {
                backgroundSelectionAdapter.selections = it
            }
            mode.observeWithPrevious { old, new ->
                fragmentChangeManager.change(old, new)
            }
            selected.observe {
                updateBackground(it)
            }
        }
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

    private fun setupBackgroundSelectionContainer() {
        binding.backgroundSelectionContainer.apply {
            setSlideOnFling(true)
            setItemTransformer(
                ScaleTransformer.Builder()
                    .setMinScale(0.6428f)
                    .build()
            )

            addOnItemChangedListener { _, position ->
                viewModel.changedSelection(position)
            }

            adapter = backgroundSelectionAdapter
        }
    }

    private fun updateBackground(backgroundSelection: BackgroundSelection?) {
        backgroundSelection ?: return
        if (backgroundSelection is BackgroundSelection.Default) {
            findFragment<EditFragment>()?.updateBackground(backgroundSelection.background)
        } else if(backgroundSelection is BackgroundSelection.Custom) {
            binding.backgroundSelectionContainer.scrollToPosition(0)
            findFragment<EditFragment>()?.updateBackground(backgroundSelection.uri)
        }
    }

    inner class Handler {
        fun close() = finish()

        fun startGallery() = galleryLauncher.launch("image/*")

        fun toggleLensFacing() = findFragment<CameraFragment>()?.toggleLens()

        fun takePicture() = findFragment<CameraFragment>()?.capture()
    }

    companion object {
        fun start(context: Context) = context.startWith<FeedUploadActivity>()

        private val EXTENSION_WHITELIST = arrayOf("PNG", "JPG")
    }
}