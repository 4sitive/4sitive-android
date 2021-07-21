package org.positive.daymotion.presentation.upload.activity

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView
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
        setupViews()
    }

    private fun setupViews() {
        binding.feedEditText.apply {
            onPressBackButton = {
                finishWithResult()
                true
            }
            setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    finishWithResult()
                    return@OnEditorActionListener true
                }
                false
            })
        }
        binding.container.setOnClickListener { finishWithResult() }
    }

    private fun finishWithResult() {
        finish()
        setResult(RESULT_OK)
    }

    companion object {
        fun start(context: Context) = context.startWith<UploadFeedTextEditActivity>()
    }
}