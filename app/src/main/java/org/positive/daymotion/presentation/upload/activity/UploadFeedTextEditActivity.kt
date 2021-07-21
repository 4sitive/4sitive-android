package org.positive.daymotion.presentation.upload.activity

import android.content.Context
import android.os.Bundle
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityUploadFeedTextEditBinding
import org.positive.daymotion.presentation.common.base.BaseActivity
import org.positive.daymotion.presentation.common.base.viewModelOf
import org.positive.daymotion.presentation.common.extension.startWith
import org.positive.daymotion.presentation.upload.viewmodel.UploadFeedTextEditViewModel

class UploadFeedTextEditActivity :
    BaseActivity<ActivityUploadFeedTextEditBinding>(R.layout.activity_upload_feed_text_edit) {

    private val viewModel by viewModelOf<UploadFeedTextEditViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        fun start(context: Context) = context.startWith<UploadFeedTextEditActivity>()
    }
}