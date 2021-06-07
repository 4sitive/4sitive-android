package org.positive.daymotion.presentation.category

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.FragmentCategoryTabBinding
import org.positive.daymotion.presentation.base.BaseFragment
import org.positive.daymotion.presentation.base.util.viewModelOf

@AndroidEntryPoint
class CategoryTabFragment :
    BaseFragment<FragmentCategoryTabBinding>(R.layout.fragment_category_tab) {

    private val viewModel by viewModelOf<CategoryTabViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        binding.button1.setOnClickListener {
            CategoryDetailActivity.start(requireContext())
        }
    }
}