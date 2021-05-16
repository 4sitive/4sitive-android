package org.positive.sms.presentation.login

import android.net.Uri
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.positive.sms.R
import org.positive.sms.databinding.ActivityLoginBinding
import org.positive.sms.presentation.base.BaseActivity

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLoginPage()
    }

    private fun loadLoginPage() {
        // TODO: depend on variant
        val url = Uri.parse("https://account.4sitive.com/oauth/authorize")
            .buildUpon()
            .appendQueryParameter("client_id", "4sitive")
            .appendQueryParameter("redirect_uri", "positive://login")
            .appendQueryParameter("response_type", "code")
            .build()
        binding.loginWebView.loadUrl(url.toString())
    }
}