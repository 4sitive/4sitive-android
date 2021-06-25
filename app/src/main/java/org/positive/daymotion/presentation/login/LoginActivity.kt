package org.positive.daymotion.presentation.login

import android.content.Context
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.TypefaceSpan
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.res.ResourcesCompat
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.BuildConfig
import org.positive.daymotion.R
import org.positive.daymotion.common.DmConstants
import org.positive.daymotion.databinding.ActivityLoginBinding
import org.positive.daymotion.extension.startOnTop
import org.positive.daymotion.presentation.base.BaseActivity
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
        val googleButton = binding.googleLoginButton
        val googleStr = "Google"
        googleButton.text = String.format(resources.getString(R.string.login_button), googleStr)
        setButton(googleButton, googleButton.text.toString(), googleStr)

        val naverButton = binding.naverLoginButton
        val naverStr = "Naver"
        naverButton.text = String.format(resources.getString(R.string.login_button), naverStr)
        setButton(naverButton, naverButton.text.toString(), naverStr)

        val facebookButton = binding.facebookLoginButton
        val facebookStr = "Facebook"
        facebookButton.text = String.format(resources.getString(R.string.login_button), facebookStr)
        setButton(facebookButton, facebookButton.text.toString(), facebookStr)

        val kakaoButton = binding.kakaoLoginButton
        val kakaoStr = "kakaoTalk"
        kakaoButton.text = String.format(resources.getString(R.string.login_button), kakaoStr)
        setButton(kakaoButton, kakaoButton.text.toString(), kakaoStr)

        val infoTextView = binding.infoTextView
        val serviceStr = "서비스 약관"
        val privacyStr = "개인정보 보호정책"
        val strArr = arrayListOf(serviceStr, privacyStr)
        setTextView(infoTextView, infoTextView.text.toString(), strArr)
    }

    private fun setButton(button: Button, str: String, word: String) {
        val start: Int = str.indexOf(word)
        val end: Int = start + word.length

        val spannable = SpannableString(str)
        spannable.setSpan(object : TypefaceSpan(null) {
            override fun updateDrawState(ds: TextPaint) {
                ds.typeface = Typeface.create(
                    ResourcesCompat.getFont(
                        this@LoginActivity,
                        R.font.kopub_dotum_bold
                    ), Typeface.NORMAL
                )
            }
        }, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        button.text = spannable

        button.setOnClickListener {
            button.alpha = 0.5f
        }
    }

    private fun setTextView(textView: TextView, str: String, words: ArrayList<String>) {
        val spannable = SpannableString(str)
        textView.movementMethod = LinkMovementMethod.getInstance()
        for (item in words) {
            val start: Int = str.indexOf(item)
            val end: Int = start + item.length

            spannable.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    when (item) {
                        words[0] -> ServiceTermsActivity.start(this@LoginActivity)
                        words[1] -> PrivacyPolicyActivity.start(this@LoginActivity)
                    }
                }

                override fun updateDrawState(ds: TextPaint) {} // 밑줄 제거

            }, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

            spannable.setSpan(object : TypefaceSpan(null) {
                override fun updateDrawState(ds: TextPaint) {
                    ds.typeface = Typeface.create(
                        ResourcesCompat.getFont(
                            applicationContext,
                            R.font.kopub_dotum_bold
                        ), Typeface.NORMAL
                    )
                }
            }, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        }
        textView.text = spannable
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