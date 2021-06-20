package org.positive.daymotion.presentation.my.activity

import android.content.Context
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityMyProfileEditBinding
import org.positive.daymotion.extension.startWith
import org.positive.daymotion.presentation.base.BaseActivity
import org.positive.daymotion.presentation.base.util.viewModelOf
import org.positive.daymotion.presentation.my.viewmodel.MyProfileEditViewModel

@AndroidEntryPoint
class MyProfileEditActivity :
    BaseActivity<ActivityMyProfileEditBinding>(R.layout.activity_my_profile_edit) {

    private val viewModel by viewModelOf<MyProfileEditViewModel>()
    private val handler by lazy { Handler() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.handler = handler

        setupObservers()
        
        viewModel.initProfile("멋쟁이 시루", "안녕하세요 시루랑 함께하는 일상을 보내고 있어요.")
    }

    private fun setupObservers() {
        viewModel.doneProfileUpdate.observe {
            finish()
        }
    }

    inner class Handler {
        fun finish() = this@MyProfileEditActivity.finish()
    }

    companion object {
        fun start(context: Context) = context.startWith<MyProfileEditActivity>()
    }
}