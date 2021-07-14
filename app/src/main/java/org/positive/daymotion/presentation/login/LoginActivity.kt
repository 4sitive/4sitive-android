package org.positive.daymotion.presentation.login

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.BuildConfig
import org.positive.daymotion.DmConstants
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityLoginBinding
import org.positive.daymotion.presentation.common.base.BaseActivity
import org.positive.daymotion.presentation.common.extension.applyClickableSpan
import org.positive.daymotion.presentation.common.extension.applyTypefaceSpan
import org.positive.daymotion.presentation.common.extension.startOnTop
import org.positive.daymotion.presentation.login.model.LoginWay
import org.positive.daymotion.presentation.terms.Terms
import org.positive.daymotion.presentation.terms.TermsActivity


@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.handler = Handler()
        setupViews()
    }

    private fun setupViews() {
        binding.googleLoginButton.apply {
            val googleStr = "Google"
            text = String.format(resources.getString(R.string.login_button), googleStr)
            applyTypefaceSpan(googleStr, R.font.kopub_dotum_bold)
        }

        binding.naverLoginButton.apply {
            val naverStr = "Naver"
            text = String.format(resources.getString(R.string.login_button), naverStr)
            applyTypefaceSpan(naverStr, R.font.kopub_dotum_bold)
        }

        binding.facebookLoginButton.apply {
            val facebookStr = "Facebook"
            text = String.format(resources.getString(R.string.login_button), facebookStr)
            applyTypefaceSpan(facebookStr, R.font.kopub_dotum_bold)
        }

        binding.kakaoLoginButton.apply {
            val kakaoStr = "kakaoTalk"
            text = String.format(resources.getString(R.string.login_button), kakaoStr)
            applyTypefaceSpan(kakaoStr, R.font.kopub_dotum_bold)
        }

        binding.infoTextView.apply {
            applyTypefaceSpan("서비스 약관", R.font.kopub_dotum_bold)
            applyTypefaceSpan("개인정보 보호정책", R.font.kopub_dotum_bold)
            applyClickableSpan("서비스 약관") {
                TermsActivity.start(this@LoginActivity, Terms.TERMS_OF_USE)
            }
            applyClickableSpan("개인정보 보호정책") {
                TermsActivity.start(this@LoginActivity, Terms.PRIVACY_POLICY)
            }
        }
    }

    inner class Handler {
        fun requestOauthLogin(loginWay: LoginWay) {
            CustomTabsIntent.Builder()
                .build()
                .launchUrl(this@LoginActivity, buildOauthRequestUrl(loginWay))
        }

        private fun buildOauthRequestUrl(loginWay: LoginWay) =
            Uri.parse(DmConstants.ACCOUNT_SERVER_BASE_URL + "/oauth/authorize")
                .buildUpon()
                .appendQueryParameter("client_id", BuildConfig.OAUTH_CLIENT_ID)
                .appendQueryParameter("redirect_uri", DmConstants.APP_SCHEME + "://login")
                .appendQueryParameter("response_type", "code")
                .appendQueryParameter("registration_hint", loginWay.registrationHint)
                .build()
    }

    companion object {
        fun startOnTop(context: Context) = context.startOnTop<LoginActivity>()
    }
}
