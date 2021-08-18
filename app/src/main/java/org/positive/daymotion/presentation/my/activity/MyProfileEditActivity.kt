package org.positive.daymotion.presentation.my.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityMyProfileEditBinding
import org.positive.daymotion.presentation.common.base.BaseActivity
import org.positive.daymotion.presentation.common.base.viewModelOf
import org.positive.daymotion.presentation.common.bundle
import org.positive.daymotion.presentation.common.extension.startForResultWith
import org.positive.daymotion.presentation.my.model.UserProfileViewData
import org.positive.daymotion.presentation.my.viewmodel.MyProfileEditViewModel


@AndroidEntryPoint
class MyProfileEditActivity :
    BaseActivity<ActivityMyProfileEditBinding>(R.layout.activity_my_profile_edit) {

    private val viewModel by viewModelOf<MyProfileEditViewModel>()
    private val handler by lazy { Handler() }

    private val originUserProfile by bundle<UserProfileViewData>()

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

        viewModel.initProfile(originUserProfile)
    }

    private fun setupObservers() {
        viewModel.doneProfileUpdate.observe {
            setResult(RESULT_OK, Intent().apply { putExtra("userProfileViewData", it) })
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
        fun startForResult(
            context: Context,
            launcher: ActivityResultLauncher<Intent>,
            originUserProfile: UserProfileViewData
        ) = context.startForResultWith<MyProfileEditActivity>(
            launcher,
            "originUserProfile" to originUserProfile
        )
    }
}