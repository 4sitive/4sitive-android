package org.positive.daymotion.presentation.setting

import android.content.Context
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityServiceTermsBinding
import org.positive.daymotion.presentation.common.extension.startWith
import org.positive.daymotion.presentation.common.base.BaseActivity

@AndroidEntryPoint
class ServiceTermsActivity : BaseActivity<ActivityServiceTermsBinding>(R.layout.activity_service_terms) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        val termFile = "file:///android_asset/terms_of_use_text.html"
        binding.webView.loadUrl(termFile)
    }

    companion object {
        fun start(context: Context) = context.startWith<ServiceTermsActivity>()
    }
}