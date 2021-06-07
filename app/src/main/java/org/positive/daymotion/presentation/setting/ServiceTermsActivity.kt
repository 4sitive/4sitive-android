package org.positive.daymotion.presentation.setting

import android.content.Context
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityMyProfileEditBinding
import org.positive.daymotion.databinding.ActivityServiceTermsBinding
import org.positive.daymotion.extension.startWith
import org.positive.daymotion.presentation.base.BaseActivity
import org.positive.daymotion.presentation.base.util.viewModelOf

@AndroidEntryPoint
class ServiceTermsActivity :
    BaseActivity<ActivityServiceTermsBinding>(R.layout.activity_service_terms) {

    companion object {
        fun start(context: Context) = context.startWith<ServiceTermsActivity>()
    }
}