package org.positive.daymotion.presentation.dispatch

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityEmptyBinding
import org.positive.daymotion.presentation.common.base.BaseActivity
import org.positive.daymotion.presentation.common.base.viewModelOf
import org.positive.daymotion.presentation.root.RootActivity

@AndroidEntryPoint
class DispatchActivity : BaseActivity<ActivityEmptyBinding>(R.layout.activity_empty) {

    private val viewModel by viewModelOf<DispatchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.data?.let {
            if (it.host == "login") {
                val code = it.getQueryParameter("code") ?: return
                viewModel.postAuthorizationCode(code)
            }
        }

        viewModel.tokenIssueCompleteEvent.observe {
            RootActivity.startOnTop(this)
        }
    }
}