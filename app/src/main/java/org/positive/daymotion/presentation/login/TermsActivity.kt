package org.positive.daymotion.presentation.login

import android.content.Context
import android.os.Bundle
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityTermsBinding
import org.positive.daymotion.presentation.common.base.BaseActivity
import org.positive.daymotion.presentation.common.base.viewModelOf
import org.positive.daymotion.presentation.common.extension.startWith
import org.positive.daymotion.presentation.login.viewmodel.TermsViewModel

class TermsActivity : BaseActivity<ActivityTermsBinding>(R.layout.activity_terms) {

    private val handler = Handler()
    private val viewModel by viewModelOf<TermsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.handler = handler
        binding.viewModel = viewModel

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        val title = intent.getStringExtra("title")
        binding.titleTextView.text = title

        setWebView(title.toString())
    }

    private fun setWebView(subject: String) {
        var termFile = ""
        if (subject.contains("개인정보")) {
            termFile = "file:///android_asset/privacy_policy_text.html"
        } else if (subject.contains("제 3자")) {
            termFile = "file:///android_asset/terms_of_use_text.html"
        }
        binding.webView.loadUrl(termFile)
    }

    inner class Handler {

    }

    companion object {
        fun start(context: Context, title: String) {
            context.startWith<TermsActivity>(Pair("title", title))
        }
    }
}