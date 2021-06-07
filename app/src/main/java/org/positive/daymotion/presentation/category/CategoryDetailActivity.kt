package org.positive.daymotion.presentation.category

import android.content.Context
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityCategoryDetailBinding
import org.positive.daymotion.extension.startWith
import org.positive.daymotion.presentation.base.BaseActivity
import org.positive.daymotion.presentation.base.util.viewModelOf

@AndroidEntryPoint
class CategoryDetailActivity : BaseActivity<ActivityCategoryDetailBinding>(R.layout.activity_category_detail) {

    private val viewModel by viewModelOf<CategoryDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
    }

    companion object {
        fun start(context: Context) = context.startWith<CategoryDetailActivity>()
    }
}