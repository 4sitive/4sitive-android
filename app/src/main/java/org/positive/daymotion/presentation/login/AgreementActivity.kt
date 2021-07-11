package org.positive.daymotion.presentation.login

import android.content.Context
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
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
    private val viewModel by viewModelOf<AgreementViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
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