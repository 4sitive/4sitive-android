package org.positive.sms.presentation.home

import android.content.Context
import dagger.hilt.android.AndroidEntryPoint
import org.positive.sms.R
import org.positive.sms.databinding.ActivityStartBinding
import org.positive.sms.extension.startOnTop
import org.positive.sms.presentation.base.BaseActivity
import org.positive.sms.presentation.login.LoginActivity

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityStartBinding>(R.layout.activity_home) {

    companion object {
        fun startOnTop(context: Context) = context.startOnTop<HomeActivity>()
    }
}