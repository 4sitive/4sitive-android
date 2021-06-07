package org.positive.daymotion.presentation.setting

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.FragmentSettingTabBinding
import org.positive.daymotion.presentation.base.BaseFragment
import org.positive.daymotion.presentation.base.util.viewModelOf

@AndroidEntryPoint
class SettingTabFragment : BaseFragment<FragmentSettingTabBinding>(R.layout.fragment_setting_tab) {

    private val viewModel by viewModelOf<SettingTabViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }
}