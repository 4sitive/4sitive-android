package org.positive.daymotion.presentation.my.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.common.bundle
import org.positive.daymotion.databinding.ActivityMyProfileEditBinding
import org.positive.daymotion.extension.startWith
import org.positive.daymotion.presentation.base.BaseActivity
import org.positive.daymotion.presentation.base.util.viewModelOf
import org.positive.daymotion.presentation.my.viewmodel.MyProfileEditViewModel


@AndroidEntryPoint
class MyProfileEditActivity :
    BaseActivity<ActivityMyProfileEditBinding>(R.layout.activity_my_profile_edit) {

    private val viewModel by viewModelOf<MyProfileEditViewModel>()
    private val handler by lazy { Handler() }

    private val originProfile by bundle<String>()

    private val originName by bundle<String>()

    private val originIntroduce by bundle<String>()

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK) {
            data?.data?.toString()?.let {
                viewModel.profileImage.value = it
            }
        }
    }

    inner class Handler {
        fun finish() = this@MyProfileEditActivity.finish()

        fun startGallery() {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            startActivityForResult(intent, REQUEST_CODE_GALLERY)
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