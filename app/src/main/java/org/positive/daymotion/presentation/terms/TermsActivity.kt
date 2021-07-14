package org.positive.daymotion.presentation.terms

import android.content.Context
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityTermsBinding
import org.positive.daymotion.presentation.common.base.BaseActivity
import org.positive.daymotion.presentation.common.bundle
import org.positive.daymotion.presentation.common.extension.startWith

@AndroidEntryPoint
class TermsActivity : BaseActivity<ActivityTermsBinding>(R.layout.activity_terms) {

    private val terms by bundle<Terms>()
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.handler = handler
        binding.title = terms.title

        setupWebView(terms)
    }

    private fun setupWebView(terms: Terms) {
        binding.webView.loadUrl(terms.htmlPath)
    }

    inner class Handler {
        fun finish() = this@TermsActivity.finish()
    }

    companion object {
        fun start(context: Context, terms: Terms) =
            context.startWith<TermsActivity>("terms" to terms)
    }
}