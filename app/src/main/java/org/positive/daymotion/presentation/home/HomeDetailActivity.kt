package org.positive.daymotion.presentation.home

import android.os.Bundle
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityHomeDetailBinding
import org.positive.daymotion.presentation.common.base.BaseActivity
import org.positive.daymotion.presentation.upload.MissionUploadActivity

class HomeDetailActivity : BaseActivity<ActivityHomeDetailBinding>(R.layout.activity_home_detail) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.handler = Handler()

        binding.backButton.setOnClickListener {
            binding.backButton.alpha = 0.5f
            onBackPressed()
        }

        binding.missionTextView.text = intent.getStringExtra("subject")

        binding.uploadButton.setOnClickListener {
            MissionUploadActivity.start(this@HomeDetailActivity)
        }
    }

    inner class Handler {

    }
}