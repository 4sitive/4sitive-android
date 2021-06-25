package org.positive.daymotion.presentation.upload

import android.content.Context
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityMissonUploadBinding
import org.positive.daymotion.extension.startWith
import org.positive.daymotion.presentation.base.BaseActivity
import org.positive.daymotion.presentation.base.util.viewModelOf

@AndroidEntryPoint
class MissionUploadActivity :
    BaseActivity<ActivityMissonUploadBinding>(R.layout.activity_misson_upload) {

    private val viewModel by viewModelOf<MissionUploadViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
    }

    companion object {
        fun start(context: Context) = context.startWith<MissionUploadActivity>()
    }
}