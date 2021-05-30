package org.positive.sms.presentation.main

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import org.positive.sms.R
import org.positive.sms.databinding.ActivityMainBinding
import org.positive.sms.extension.startOnTop
import org.positive.sms.extension.viewModelOf
import org.positive.sms.presentation.base.BaseActivity


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel by viewModelOf<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

        binding.button.setOnClickListener {
            startGallery()
        }
    }

    private fun startGallery() {
        // TODO(yh): Needs to be modified with Result Api
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(intent, 200)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 200) {
            if (resultCode == RESULT_OK && data != null) {
                // TODO(yh): Needs to be modified with Kotlin Apis
                val inputStream = contentResolver.openInputStream(data.data!!)
                val img = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()
                binding.image.setImageBitmap(img)
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