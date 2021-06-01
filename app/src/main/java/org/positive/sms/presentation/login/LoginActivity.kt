package org.positive.sms.presentation.login

import android.content.Context
import android.net.Uri
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.positive.sms.BuildConfig
import org.positive.sms.R
import org.positive.sms.common.PsConstants
import org.positive.sms.databinding.ActivityLoginBinding
import org.positive.sms.extension.startOnTop
import org.positive.sms.presentation.base.BaseActivity

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLoginPage()
    }

    private fun loadLoginPage() {
        val url = Uri.parse(PsConstants.ACCOUNT_SERVER_BASE_URL + "/oauth/authorize")
            .buildUpon()
            .appendQueryParameter("client_id", BuildConfig.OAUTH_CLIENT_ID)
            .appendQueryParameter("redirect_uri", PsConstants.APP_SCHEME + "://login")
            .appendQueryParameter("response_type", "code")
            .build()
        binding.loginWebView.loadUrl(url.toString())
    }

    companion object {
        fun startOnTop(context: Context) = context.startOnTop<LoginActivity>()
    }
}