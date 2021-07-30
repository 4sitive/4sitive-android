package org.positive.daymotion.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityPostListBinding
import org.positive.daymotion.presentation.common.base.BaseActivity
import org.positive.daymotion.presentation.common.base.viewModelOf
import org.positive.daymotion.presentation.upload.activity.FeedUploadActivity

@AndroidEntryPoint
class PostListActivity :
    BaseActivity<ActivityPostListBinding>(R.layout.activity_post_list) {

    private val viewModel by viewModelOf<PostListViewModel>()
    private val handler by lazy { Handler() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.handler = handler

        val mission = intent.getStringExtra("mission")

        binding.whiteBackButton.setOnClickListener {
            finish()
        }
        binding.blackBackButton.setOnClickListener {
            finish()
        }

        binding.goToUploadButton.setOnClickListener {
            val intent = Intent(this, FeedUploadActivity::class.java)
            intent.putExtra("mission", mission)
            startActivity(intent)
        }
        binding.minUploadButton.setOnClickListener {
            val intent = Intent(this, FeedUploadActivity::class.java)
            intent.putExtra("mission", mission)
            startActivity(intent)
        }

        binding.missionTextView.text = mission
        binding.titleTextView.text = mission

        binding.homeMissionCard.background =
            ContextCompat.getDrawable(this, intent.getIntExtra("color", 0))
        binding.CardEffectImageView.setImageResource(intent.getIntExtra("effect", 0))


    }

    inner class Handler {

    }

}

