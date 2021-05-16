package org.positive.sms.presentation.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import dagger.hilt.android.AndroidEntryPoint
import org.positive.sms.R
import org.positive.sms.databinding.ActivityLoginBinding
import org.positive.sms.extension.viewModelOf
import org.positive.sms.presentation.base.BaseActivity

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    val viewModel by viewModelOf<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding.loginWebView) {
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    if (request?.url?.scheme == "positive") {
                        startActivity(Intent(Intent.ACTION_VIEW, request.url))
                        return true
                    }
                    return false
                }
            }
            settings.textZoom = 100

            val uri = Uri.parse("https://account.4sitive.com/oauth/authorize")
                .buildUpon()
                .appendQueryParameter("client_id", "4sitive")
                .appendQueryParameter("redirect_uri", "positive://login")
                .appendQueryParameter("response_type", "code")
                .build()

            loadUrl(uri.toString())
//
//            postDelayed(
//                {
//                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("positive://login")))
//                }, 3000
//            )
        }
    }
}