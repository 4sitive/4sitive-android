package org.befour.sms.presentation.main

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.befour.sms.R
import org.befour.sms.databinding.ActivityMainBinding
import org.befour.sms.extension.viewModelOf
import org.befour.sms.presentation.base.BaseActivity

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel by viewModelOf<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
    }
}