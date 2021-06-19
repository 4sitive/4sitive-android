package org.positive.daymotion.presentation.login

import android.content.Context
import android.content.Intent
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

        val googleBtn = binding.googleLoginButton
        val googleStr = "Google"
        googleBtn.text = String.format(resources.getString(R.string.login_button), googleStr)
        btnSet(googleBtn, googleBtn.text.toString(), googleStr)

        val naverBtn = binding.naverLoginButton
        val naverStr = "Naver"
        naverBtn.text = String.format(resources.getString(R.string.login_button), naverStr)
        btnSet(naverBtn, naverBtn.text.toString(), naverStr)

        val facebookBtn = binding.facebookLoginButton
        val facebookStr = "Facebook"
        facebookBtn.text = String.format(resources.getString(R.string.login_button), facebookStr)
        btnSet(facebookBtn, facebookBtn.text.toString(), facebookStr)

        val kakaoBtn = binding.kakaoLoginButton
        val kakaoStr = "kakaoTalk"
        kakaoBtn.text = String.format(resources.getString(R.string.login_button), kakaoStr)
        btnSet(kakaoBtn, kakaoBtn.text.toString(), kakaoStr)

        val tvInfo = binding.tvInfo
        val serviceStr = "서비스 약관"
        val privacyStr = "개인정보 보호정책"
        val strArr = arrayListOf(serviceStr, privacyStr)
        tvSet(tvInfo, tvInfo.text.toString(), strArr)
    }

    private fun btnSet(btn: Button, str: String, word: String) {
        val start: Int = str.indexOf(word)
        val end: Int = start + word.length

        val spannable = SpannableString(str)
        spannable.setSpan(object : TypefaceSpan(null) {
            override fun updateDrawState(ds: TextPaint) {
                ds.typeface = Typeface.create(ResourcesCompat.getFont(applicationContext, R.font.kopub_dotum_bold), Typeface.NORMAL)
            }
        }, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        btn.text = spannable

        btn.setOnClickListener(){
            btn.alpha = 0.5f
        }
    }

    private fun tvSet(tv: TextView, str: String, words: ArrayList<String>) {
        val spannable = SpannableString(str)
        for (item in words) {
            val start: Int = str.indexOf(item)
            val end: Int = start + item.length

            tv.movementMethod = LinkMovementMethod.getInstance();
            spannable.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    when(item){
                        words[0] -> {
                            intent = Intent(applicationContext, ServiceTermsActivity::class.java)
                            startActivity(intent)
                        }
                        words[1] -> {
                            intent = Intent(applicationContext, PrivacyPolicyActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }

                override fun updateDrawState(ds: TextPaint) {
                    ds.linkColor = -0x1000000
                    ds.typeface = Typeface.create(ResourcesCompat.getFont(applicationContext, R.font.kopub_dotum_bold), Typeface.NORMAL)
                    super.updateDrawState(ds)
                }
            }, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        }
        tv.text = spannable
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