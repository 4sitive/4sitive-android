package org.positive.daymotion.presentation.home

import android.content.Context
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityStartBinding
import org.positive.daymotion.extension.startOnTop
import org.positive.daymotion.presentation.base.BaseActivity

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityStartBinding>(R.layout.activity_home) {

    companion object {
        fun startOnTop(context: Context) = context.startOnTop<HomeActivity>()
    }
}