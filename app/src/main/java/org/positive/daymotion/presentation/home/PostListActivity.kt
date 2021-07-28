package org.positive.daymotion.presentation.home

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.drawToBitmap
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityPostListBinding
import org.positive.daymotion.presentation.common.base.BaseActivity
import org.positive.daymotion.presentation.common.base.viewModelOf
import org.positive.daymotion.presentation.upload.MissionUploadActivity
import java.nio.ByteBuffer

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
            val intent = Intent(this, MissionUploadActivity::class.java)
            intent.putExtra("mission", mission)
            startActivity(intent)
        }
        binding.minUploadButton.setOnClickListener {
            val intent = Intent(this, MissionUploadActivity::class.java)
            intent.putExtra("mission", mission)
            startActivity(intent)
        }

        binding.missionTextView.text = mission
        binding.titleTextView.text = mission

        binding.homeMissionCard.background = ContextCompat.getDrawable(this, intent.getIntExtra("color", 0))
        binding.CardEffectImageView.setImageResource(intent.getIntExtra("effect", 0))


    }

    inner class Handler {

    }

}

