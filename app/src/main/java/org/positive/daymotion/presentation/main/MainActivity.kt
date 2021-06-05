package org.positive.daymotion.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.common.PsConstants
import org.positive.daymotion.databinding.ActivityMainBinding
import org.positive.daymotion.extension.startOnTop
import org.positive.daymotion.extension.viewModelOf
import org.positive.daymotion.presentation.base.BaseActivity


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel by viewModelOf<MainViewModel>()
    private var backWait: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

        binding.button.setOnClickListener {
            startGallery()
        }

        viewModel.image.observe {
            if (it != null) {
                Glide.with(this)
                    .load(PsConstants.CDN_SERVER_BASE_URL.removeSuffix("/") + it)
                    .into(binding.image)
            }
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            // TODO(je): create fragment and setting listener
            true
        }
    }

    override fun onBackPressed() {
        if(System.currentTimeMillis() - backWait >=2000 ) {
            backWait = System.currentTimeMillis()
            Toast.makeText(applicationContext, "press BACK again to exit.", Toast.LENGTH_LONG).show()
        } else {
            finish()
        }
    }

    private fun startGallery() {
        // TODO(yh): Neceds to be modified with Result Api
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(intent, 200)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 200) {
            if (resultCode == RESULT_OK && data != null) {
                viewModel.upload(data.data!!.toString())
            } else {
                Toast.makeText(this, "Image not selected", Toast.LENGTH_SHORT).show()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        fun startOnTop(context: Context) = context.startOnTop<MainActivity>()
    }
}