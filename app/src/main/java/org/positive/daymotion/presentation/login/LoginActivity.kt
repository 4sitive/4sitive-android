package org.positive.daymotion.presentation.login

import android.content.Context
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityLoginBinding
import org.positive.daymotion.presentation.common.base.BaseActivity
import org.positive.daymotion.presentation.common.extension.applyClickableSpan
import org.positive.daymotion.presentation.common.extension.applyTypefaceSpan
import org.positive.daymotion.presentation.common.extension.startOnTop
import org.positive.daymotion.presentation.login.model.LoginWay
import org.positive.daymotion.presentation.setting.PrivacyPolicyActivity
import org.positive.daymotion.presentation.setting.ServiceTermsActivity


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
            applyClickableSpan("서비스 약관") { ServiceTermsActivity.start(this@LoginActivity) }
            applyClickableSpan("개인정보 보호정책") { PrivacyPolicyActivity.start(this@LoginActivity) }
        }
    }

    inner class Handler {
        fun startAgreementActivity(loginWay: LoginWay) {
            AgreementActivity.start(this@LoginActivity, loginWay)
        }
    }

    companion object {
        fun startOnTop(context: Context) = context.startOnTop<LoginActivity>()
    }
}
