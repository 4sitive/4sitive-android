package org.positive.daymotion.presentation.setting

import android.content.Context
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityPrivacyPolicyBinding
import org.positive.daymotion.extension.startWith
import org.positive.daymotion.presentation.base.BaseActivity

@AndroidEntryPoint
class PrivacyPolicyActivity :
    BaseActivity<ActivityPrivacyPolicyBinding>(R.layout.activity_privacy_policy) {

    companion object {
        fun start(context: Context) = context.startWith<PrivacyPolicyActivity>()
    }
}