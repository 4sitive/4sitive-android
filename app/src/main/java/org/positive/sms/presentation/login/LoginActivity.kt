package org.positive.sms.presentation.login

import android.os.Bundle
import android.webkit.WebViewClient
import dagger.hilt.android.AndroidEntryPoint
import org.positive.sms.R
import org.positive.sms.databinding.ActivityLoginBinding
import org.positive.sms.presentation.base.BaseActivity

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding.loginWebView) {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            loadUrl("https://account.4sitive.com/login")
        }
    }
}