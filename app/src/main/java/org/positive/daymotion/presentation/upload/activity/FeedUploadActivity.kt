package org.positive.daymotion.presentation.upload.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.activity.result.ActivityResultLauncher
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
import org.positive.daymotion.presentation.common.bundle
import org.positive.daymotion.presentation.common.extension.findFragment
import org.positive.daymotion.presentation.common.extension.startForResultWith
import org.positive.daymotion.presentation.common.showPopupDialog
import org.positive.daymotion.presentation.common.util.recursiveFileSearch
import org.positive.daymotion.presentation.upload.FragmentChangeManager
import org.positive.daymotion.presentation.upload.adapter.BackgroundSelectionAdapter
import org.positive.daymotion.presentation.upload.fragment.CameraFragment
import org.positive.daymotion.presentation.upload.fragment.MissionSelectBottomSheetDialogFragment
import org.positive.daymotion.presentation.upload.fragment.UploadFeedConfirmFragment
import org.positive.daymotion.presentation.upload.model.BackgroundSelection
import org.positive.daymotion.presentation.upload.model.MissionViewItem
import org.positive.daymotion.presentation.upload.viewmodel.FeedUploadViewModel
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*


@AndroidEntryPoint
class FeedUploadActivity :
    BaseActivity<ActivityFeedUploadBinding>(R.layout.activity_feed_upload),
    CameraFragment.EventListener,
    MissionSelectBottomSheetDialogFragment.EventListener {

    private val missionId by bundle<String?>()
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

        viewModel.loadTodayMissions(missionId)
    }

    override fun onBackPressed() {
        viewModel.close()
    }

    override fun onCameraStateChange(isAvailableToggle: Boolean) {
        viewModel.setToggleAvailable(isAvailableToggle)
    }

    override fun onImageSaved(uri: Uri) {
        viewModel.selectCustomImage(BackgroundSelection.Custom(uri))
    }

    override fun onMissionSelected(mission: MissionViewItem) {
        viewModel.selectMission(mission)
    }

    private fun setupFragments() {
        with(supportFragmentManager) {
            commit {
                add(R.id.container, CameraFragment::class.java, null)
                add(R.id.container, UploadFeedConfirmFragment::class.java, null)
                runOnCommit {
                    commit { findFragment<UploadFeedConfirmFragment>()?.let { hide(it) } }
                }
            }
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
            selectedBackgroundSelection.observeNonNull {
                updateBackground(it)
            }
            finish.observe {
                showFinishAlert()
            }
            showMissionList.observeNonNull {
                val (selected, missions) = it
                showMissionSelectBottomSheet(selected, missions)
            }
            selectedMission.observeNonNull {
                findFragment<UploadFeedConfirmFragment>()?.updateSelectedMission(it)
            }
            uploadDone.observe {
                setResult(RESULT_OK)
                finish()
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
            setItemTransformer(
                ScaleTransformer.Builder()
                    .setMinScale(0.6428f)
                    .build()
            )

            addOnItemChangedListener { _, position ->
                viewModel.changedBackgroundSelection(position)
            }

            adapter = backgroundSelectionAdapter
        }
    }

    private fun updateBackground(backgroundSelection: BackgroundSelection) {
        if (backgroundSelection is BackgroundSelection.Custom) {
            binding.backgroundSelectionContainer.scrollToPosition(0)
            findFragment<UploadFeedConfirmFragment>()?.updateBackground(backgroundSelection.uri)
        } else if (backgroundSelection is BackgroundSelection.Default) {
            findFragment<UploadFeedConfirmFragment>()?.updateBackground(backgroundSelection.background)
        }
    }

    private fun showFinishAlert() {
        showPopupDialog {
            title = "피드를 삭제할까요?"
            content = "삭제하시면 지금까지 작업한\n모든 내용이 삭제됩니다."
            blueButtonText = "삭제 할래요!"
            grayButtonText = "아니에요. 이어서 작업할래요."
            isVisibleGrayButton = true
            isCancelable = true
            onClickBlueButton {
                setResult(RESULT_OK)
                finish()
            }
        }
    }

    private fun showMissionSelectBottomSheet(
        selected: MissionViewItem,
        missions: List<MissionViewItem>
    ) {
        val fragment = MissionSelectBottomSheetDialogFragment.newInstance(
            selected,
            missions.toTypedArray()
        )
        fragment.show(
            supportFragmentManager,
            MissionSelectBottomSheetDialogFragment::class.simpleName
        )
    }

    private fun screenShot(): Bitmap {
        val captureTarget = binding.container
        captureTarget.isDrawingCacheEnabled = true
        return captureTarget.drawingCache
    }

    private fun getOrCreateOutputDir(context: Context): File {
        val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
            File(it, context.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else context.filesDir
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    private fun createCapturedImageFile(bitmap: Bitmap): String {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes)
        val file = File(getOrCreateOutputDir(this), "capture.jpg")
        try {
            file.createNewFile()
            val outputStream = FileOutputStream(file)
            outputStream.write(bytes.toByteArray())
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Uri.fromFile(file).toString()
    }

    inner class Handler {

        fun startGallery() = galleryLauncher.launch("image/*")

        fun toggleLensFacing() = findFragment<CameraFragment>()?.toggleLens()

        fun takePicture() = findFragment<CameraFragment>()?.capture()

        fun viewCapture() {
            lifecycleScope.launch {
                showLoading(true)
                val imageUri = withContext(Dispatchers.IO) {
                    val bitmap = screenShot()
                    createCapturedImageFile(bitmap)
                }
                showLoading(false)
                viewModel.uploadFeed(imageUri)
            }
        }
    }

    companion object {
        fun startForResult(
            context: Context,
            launcher: ActivityResultLauncher<Intent>
        ) = context.startForResultWith<FeedUploadActivity>(launcher)

        fun startForResult(
            context: Context,
            launcher: ActivityResultLauncher<Intent>,
            missionId: String
        ) = context.startForResultWith<FeedUploadActivity>(
            launcher,
            "missionId" to missionId
        )

        private val EXTENSION_WHITELIST = arrayOf("PNG", "JPG")
    }
}