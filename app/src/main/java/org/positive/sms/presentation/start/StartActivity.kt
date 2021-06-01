package org.positive.sms.presentation.start

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.positive.sms.R
import org.positive.sms.databinding.ActivityStartBinding
import org.positive.sms.extension.viewModelOf
import org.positive.sms.presentation.base.BaseActivity
import org.positive.sms.presentation.home.HomeActivity
import org.positive.sms.presentation.login.LoginActivity
import org.positive.sms.presentation.main.MainActivity

@AndroidEntryPoint
class StartActivity : BaseActivity<ActivityStartBinding>(R.layout.activity_start) {

    private val viewModel by viewModelOf<StartViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.checkIssuedToken()
        viewModel.alreadyTokenIssued.observeNonNull {
            if (it) {
                MainActivity.startOnTop(this)
            } else {
                LoginActivity.startOnTop(this)
            }
        }
    }
}