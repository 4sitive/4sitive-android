package org.positive.daymotion.presentation.setting

import android.content.Context
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityServiceTermsBinding
import org.positive.daymotion.presentation.common.extension.startWith
import org.positive.daymotion.presentation.common.base.BaseActivity

@AndroidEntryPoint
class ServiceTermsActivity :
    BaseActivity<ActivityServiceTermsBinding>(R.layout.activity_service_terms) {

    companion object {
        fun start(context: Context) = context.startWith<ServiceTermsActivity>()
    }
}