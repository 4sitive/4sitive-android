package org.positive.daymotion.presentation.dispatch

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityEmptyBinding
import org.positive.daymotion.extension.viewModelOf
import org.positive.daymotion.presentation.base.BaseActivity
import org.positive.daymotion.presentation.main.MainActivity

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
            Toast.makeText(this, "token issue complete", Toast.LENGTH_SHORT).show()
            Handler(mainLooper).postDelayed({ MainActivity.startOnTop(this) }, 3000)
        }
    }
}