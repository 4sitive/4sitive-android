package org.positive.daymotion.presentation.my.activity

import android.content.Context
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityMyProfileEditBinding
import org.positive.daymotion.presentation.common.base.BaseActivity
import org.positive.daymotion.presentation.common.base.viewModelOf
import org.positive.daymotion.presentation.common.bundle
import org.positive.daymotion.presentation.common.extension.startWith
import org.positive.daymotion.presentation.my.viewmodel.MyProfileEditViewModel


@AndroidEntryPoint
class MyProfileEditActivity :
    BaseActivity<ActivityMyProfileEditBinding>(R.layout.activity_my_profile_edit) {

    private val viewModel by viewModelOf<MyProfileEditViewModel>()
    private val handler by lazy { Handler() }

    private val originProfile by bundle<String>()
    private val originName by bundle<String>()
    private val originIntroduce by bundle<String>()

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        it?.let {
            viewModel.profileImage.value = it.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.handler = handler

        setupObservers()

        viewModel.initProfile(originProfile, originName, originIntroduce)
    }

    private fun setupObservers() {
        viewModel.doneProfileUpdate.observe {
            finish()
        }
    }

    inner class Handler {
        fun finish() = this@MyProfileEditActivity.finish()

        fun startGallery() {
            galleryLauncher.launch("image/*")
        }
    }

    companion object {
        private const val REQUEST_CODE_GALLERY = 1000

        fun start(
            context: Context,
            originProfile: String,
            originName: String,
            originIntroduce: String
        ) = context.startWith<MyProfileEditActivity>(
            "originProfile" to originProfile,
            "originName" to originName,
            "originIntroduce" to originIntroduce
        )
    }
}