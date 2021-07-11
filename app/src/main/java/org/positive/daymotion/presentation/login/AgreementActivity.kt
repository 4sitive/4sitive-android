package org.positive.daymotion.presentation.login

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.BuildConfig
import org.positive.daymotion.DmConstants
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityAgreementBinding
import org.positive.daymotion.presentation.common.base.BaseActivity
import org.positive.daymotion.presentation.common.base.viewModelOf
import org.positive.daymotion.presentation.common.bundle
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
    }

    inner class Handler {
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