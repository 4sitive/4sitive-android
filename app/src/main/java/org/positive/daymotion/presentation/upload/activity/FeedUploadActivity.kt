package org.positive.daymotion.presentation.upload.activity

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityFeedUploadBinding
import org.positive.daymotion.presentation.common.base.BaseActivity
import org.positive.daymotion.presentation.common.base.viewModelOf
import org.positive.daymotion.presentation.common.extension.startWith
import org.positive.daymotion.presentation.upload.FeedUploadViewModel
import org.positive.daymotion.presentation.upload.fragment.CameraFragment

@AndroidEntryPoint
class FeedUploadActivity :
    BaseActivity<ActivityFeedUploadBinding>(R.layout.activity_feed_upload) {

    private val viewModel by viewModelOf<FeedUploadViewModel>()
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.handler = handler

        supportFragmentManager.commit {
            add(R.id.container, CameraFragment::class.java, null)
        }
    }

    inner class Handler {
        fun close() = finish()

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
    }
}