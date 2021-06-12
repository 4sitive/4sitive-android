package org.positive.daymotion.presentation.login

import android.content.Context
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.TypefaceSpan
import android.text.util.Linkify
import android.widget.Button
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.BuildConfig
import org.positive.daymotion.R
import org.positive.daymotion.common.DmConstants
import org.positive.daymotion.databinding.ActivityLoginBinding
import org.positive.daymotion.extension.startOnTop
import org.positive.daymotion.presentation.base.BaseActivity
import java.util.regex.Pattern


@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.handler = Handler()

        window.statusBarColor = ContextCompat.getColor(applicationContext!!, R.color.colorWhite)

        fun btnSet(btn: Button, str: String, word: String) {
            val start: Int = str.indexOf(word)
            val end: Int = start + word.length

            val spannable = SpannableString(str)
            spannable.setSpan(object : TypefaceSpan(null) {
                override fun updateDrawState(ds: TextPaint) {
                    ds.typeface = Typeface.create(ResourcesCompat.getFont(applicationContext, R.font.kopub_dotum_bold), Typeface.NORMAL)
                }
            }, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            btn.text = spannable
        }

        val googleBtn = findViewById<Button>(R.id.googleLoginButton)
        val googleStr = "Google"
        googleBtn.text = String.format(resources.getString(R.string.login_button), googleStr)
        btnSet(googleBtn, googleBtn.text.toString(), googleStr)

        val naverBtn = findViewById<Button>(R.id.naverLoginButton)
        val naverStr = "Naver"
        naverBtn.text = String.format(resources.getString(R.string.login_button), naverStr)
        btnSet(naverBtn, naverBtn.text.toString(), naverStr)

        val facebookBtn = findViewById<Button>(R.id.facebookLoginButton)
        val facebookStr = "Facebook"
        facebookBtn.text = String.format(resources.getString(R.string.login_button), facebookStr)
        btnSet(facebookBtn, facebookBtn.text.toString(), facebookStr)

        val kakaoBtn = findViewById<Button>(R.id.kakaoLoginButton)
        val kakaoStr = "kakaoTalk"
        kakaoBtn.text = String.format(resources.getString(R.string.login_button), kakaoStr)
        btnSet(kakaoBtn, kakaoBtn.text.toString(), kakaoStr)

        fun tvSet(tv: TextView, str: String, words: ArrayList<String>) {
            val spannable = SpannableString(str)
            for(item in words){
                val start: Int = str.indexOf(item)
                val end: Int = start + item.length

                spannable.setSpan(object : TypefaceSpan(null) {
                    override fun updateDrawState(ds: TextPaint) {
                        ds.typeface = Typeface.create(ResourcesCompat.getFont(applicationContext, R.font.kopub_dotum_bold), Typeface.NORMAL)
                    }
                }, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            }
            tv.text = spannable

            val mTransform = Linkify.TransformFilter { match, url -> "" }

            val serviceUrl = "https://www.naver.com/"
            val privacyUrl = "https://www.daum.net/"
            val urlArr = arrayListOf(serviceUrl, privacyUrl)

            for((i, item) in words.withIndex()){
                val pattern = Pattern.compile(item)
                Linkify.addLinks(tv, pattern, urlArr[i], null, mTransform)
            }
        }

        val tvInfo = findViewById<TextView>(R.id.tvInfo)
        val serviceStr = "서비스 약관"
        val privacyStr = "개인정보 보호정책"
        val strArr = arrayListOf(serviceStr, privacyStr)
        tvSet(tvInfo, tvInfo.text.toString(), strArr)
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