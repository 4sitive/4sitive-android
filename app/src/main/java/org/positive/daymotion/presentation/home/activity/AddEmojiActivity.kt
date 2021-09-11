package org.positive.daymotion.presentation.home.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityAddEmojiBinding
import org.positive.daymotion.presentation.common.base.BaseActivity
import org.positive.daymotion.presentation.common.base.viewModelOf
import org.positive.daymotion.presentation.common.bundle
import org.positive.daymotion.presentation.common.extension.startForResultWith
import org.positive.daymotion.presentation.home.model.EmojiItem
import org.positive.daymotion.presentation.home.viewmodel.AddEmojiViewModel

@AndroidEntryPoint
class AddEmojiActivity : BaseActivity<ActivityAddEmojiBinding>(R.layout.activity_add_emoji) {

    private val viewModel by viewModelOf<AddEmojiViewModel>()
    private val feedId by bundle<String>()
    private val emojis by bundle<ArrayList<EmojiItem>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color._99000000)
        binding.viewModel = viewModel
        viewModel.setupData(feedId, emojis.toList())
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.updatedEmojis.observeNonNull {
            setResult(RESULT_OK, Intent().apply { putExtra("updatedEmojis", ArrayList(it)) })
            finish()
        }
    }


    companion object {
        fun startForResult(
            context: Context,
            launcher: ActivityResultLauncher<Intent>,
            feedId: String,
            emojis: ArrayList<EmojiItem>
        ) = context.startForResultWith<AddEmojiActivity>(
            launcher,
            "feedId" to feedId,
            "emojis" to emojis
        )
    }
}

