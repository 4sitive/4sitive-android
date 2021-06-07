package org.positive.daymotion.presentation.login

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.BuildConfig
import org.positive.daymotion.R
import org.positive.daymotion.common.DmConstants
import org.positive.daymotion.databinding.ActivityLoginBinding
import org.positive.daymotion.extension.startOnTop
import org.positive.daymotion.presentation.base.BaseActivity

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.handler = Handler()
    }

    inner class Handler {
        // TODO(yh): need loading bar
        fun requestLogin(loginWay: LoginWay) {
            val url = Uri.parse(DmConstants.ACCOUNT_SERVER_BASE_URL + "/oauth/authorize")
                .buildUpon()
                .appendQueryParameter("client_id", BuildConfig.OAUTH_CLIENT_ID)
                .appendQueryParameter("redirect_uri", DmConstants.APP_SCHEME + "://login")
                .appendQueryParameter("response_type", "code")
                .appendQueryParameter("registration_hint", loginWay.registrationHint)
                .build()

            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(this@LoginActivity, url)
        }
    }

    enum class LoginWay(val registrationHint: String) {
        KAKAO("KAKAO"), GOOGLE("GOOGLE")
    }

    companion object {
        fun startOnTop(context: Context) = context.startOnTop<LoginActivity>()
    }
}