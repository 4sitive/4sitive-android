package org.positive.daymotion.presentation.start

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityStartBinding
import org.positive.daymotion.extension.viewModelOf
import org.positive.daymotion.presentation.base.BaseActivity
import org.positive.daymotion.presentation.login.LoginActivity
import org.positive.daymotion.presentation.main.MainActivity

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