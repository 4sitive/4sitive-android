package org.positive.daymotion.presentation.login

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.BuildConfig
import org.positive.daymotion.DmConstants
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityAgreementBinding
import org.positive.daymotion.presentation.common.base.BaseActivity
import org.positive.daymotion.presentation.common.base.viewModelOf
import org.positive.daymotion.presentation.common.bundle
import org.positive.daymotion.presentation.common.extension.applyTypefaceSpan
import org.positive.daymotion.presentation.common.extension.startWith
import org.positive.daymotion.presentation.login.model.LoginWay
import org.positive.daymotion.presentation.login.viewmodel.AgreementViewModel

@AndroidEntryPoint
class AgreementActivity : BaseActivity<ActivityAgreementBinding>(R.layout.activity_agreement) {

    private val loginWay by bundle<LoginWay>()

    private val handler = Handler()
    private val viewModel by viewModelOf<AgreementViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.handler = handler
        binding.viewModel = viewModel
        setupViews()
    }

    private fun setupViews() {
        with(binding) {
            firstAgreementTextView.applyTypefaceSpan("[필수]", R.font.kopub_dotum_bold)
            secondAgreementTextView.applyTypefaceSpan("[필수]", R.font.kopub_dotum_bold)
            thirdAgreementTextView.applyTypefaceSpan("[선택]", R.font.kopub_dotum_bold)

            // 글자 눌렀을 때도 체크되면 좋을거 같아서 추가했는데.. 이상하면 빼셔도 됩니다!
            firstAgreementTextView.setOnClickListener {firstAgreementCheckBox.isChecked = !firstAgreementCheckBox.isChecked}
            secondAgreementTextView.setOnClickListener {secondAgreementCheckBox.isChecked = !secondAgreementCheckBox.isChecked}
            thirdAgreementTextView.setOnClickListener {thirdAgreementCheckBox.isChecked = !thirdAgreementCheckBox.isChecked}
            allAgreeTextView.setOnClickListener{
                allAgreeCheckBox.isChecked = !allAgreeCheckBox.isChecked
                firstAgreementCheckBox.isChecked = allAgreeCheckBox.isChecked
                secondAgreementCheckBox.isChecked = allAgreeCheckBox.isChecked
                thirdAgreementCheckBox.isChecked = allAgreeCheckBox.isChecked
            }
        }
    }

    inner class Handler {
        fun startTermsActivity(title: TextView) {
            TermsActivity.start(this@AgreementActivity, title.text.toString())
        }

        fun requestOauthLogin() {
            CustomTabsIntent.Builder()
                .build()
                .launchUrl(this@AgreementActivity, buildOauthRequestUrl())
        }

        private fun buildOauthRequestUrl() =
            Uri.parse(DmConstants.ACCOUNT_SERVER_BASE_URL + "/oauth/authorize")
                .buildUpon()
                .appendQueryParameter("client_id", BuildConfig.OAUTH_CLIENT_ID)
                .appendQueryParameter("redirect_uri", DmConstants.APP_SCHEME + "://login")
                .appendQueryParameter("response_type", "code")
                .appendQueryParameter("registration_hint", loginWay.registrationHint)
                .build()
    }

    companion object {
        fun start(
            context: Context,
            loginWay: LoginWay
        ) = context.startWith<AgreementActivity>(
            "loginWay" to loginWay
        )
    }
}