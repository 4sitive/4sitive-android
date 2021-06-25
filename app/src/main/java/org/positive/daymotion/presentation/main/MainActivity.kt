package org.positive.daymotion.presentation.main

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.DmConstants
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityMainBinding
import org.positive.daymotion.presentation.common.base.BaseActivity
import org.positive.daymotion.presentation.common.base.viewModelOf
import org.positive.daymotion.presentation.common.extension.startOnTop


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel by viewModelOf<MainViewModel>()
    private var backWait: Long = 0

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                viewModel.upload(uri.toString())
            } else {
                Toast.makeText(this, "Image not selected", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

        binding.button.setOnClickListener {
            startGallery()
        }

        viewModel.image.observe {
            if (it != null) {
                Glide.with(this)
                    .load(DmConstants.CDN_SERVER_BASE_URL.removeSuffix("/") + it)
                    .into(binding.image)
            }
        }
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - backWait >= 2000) {
            backWait = System.currentTimeMillis()
            Toast.makeText(applicationContext, "press BACK again to exit.", Toast.LENGTH_LONG)
                .show()
        } else {
            finish()
        }
    }

    private fun startGallery() {
        getContent.launch("image/*")
    }

    companion object {
        fun startOnTop(context: Context) = context.startOnTop<MainActivity>()
    }
}