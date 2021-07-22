package org.positive.daymotion.presentation.upload.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityUploadFeedTextEditBinding
import org.positive.daymotion.presentation.common.base.BaseActivity
import org.positive.daymotion.presentation.common.base.viewModelOf
import org.positive.daymotion.presentation.common.bundle
import org.positive.daymotion.presentation.common.extension.startForResultWith
import org.positive.daymotion.presentation.upload.model.TextEditConfig
import org.positive.daymotion.presentation.upload.viewmodel.UploadFeedTextEditViewModel

class UploadFeedTextEditActivity :
    BaseActivity<ActivityUploadFeedTextEditBinding>(R.layout.activity_upload_feed_text_edit) {

    private val viewModel by viewModelOf<UploadFeedTextEditViewModel>()
    private val textEditConfig by bundle<TextEditConfig>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        setupViews()
        setupObservers()

        viewModel.initWithConfig(textEditConfig)
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
        binding.container.setOnClickListener {
            finishWithResult()
        }
    }

    private fun setupObservers() {
        with(viewModel) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                textColor.observeNonNull {
                    binding.feedEditText.apply {
                        textSelectHandle?.setTint(it)
                        textSelectHandleLeft?.setTint(it)
                        textSelectHandleRight?.setTint(it)
                        textCursorDrawable?.setTint(it)
                    }
                }
            }
        }
    }

    private fun finishWithResult() {
        val textAlignment = viewModel.textAlignment.value
        val textColor = viewModel.textColor.value
        val editingText = viewModel.editingText.value

        if (textAlignment != null && textColor != null && editingText != null) {
            val textEditConfig = TextEditConfig(textAlignment, textColor, editingText)
            setResult(RESULT_OK, Intent().apply { putExtra("textEditConfig", textEditConfig) })
        } else {
            setResult(RESULT_CANCELED)
        }
        finish()
    }

    companion object {
        val contract = object : ActivityResultContract<Intent, TextEditConfig?>() {
            override fun createIntent(
                context: Context,
                input: Intent
            ) = input

            override fun parseResult(
                resultCode: Int,
                intent: Intent?
            ) = intent?.extras?.get("textEditConfig")?.let { it as TextEditConfig }
        }

        fun startForResult(
            context: Context,
            launcher: ActivityResultLauncher<Intent>,
            textEditConfig: TextEditConfig
        ) = context.startForResultWith<UploadFeedTextEditActivity>(
            launcher, "textEditConfig" to textEditConfig
        )
    }
}