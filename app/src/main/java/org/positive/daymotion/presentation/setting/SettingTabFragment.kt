package org.positive.daymotion.presentation.setting

import android.os.Bundle
import android.view.View
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.FragmentSettingTabBinding
import org.positive.daymotion.presentation.base.BaseFragment
import org.positive.daymotion.presentation.base.util.viewModelOf
import org.positive.daymotion.presentation.root.model.RootTabFragment

@AndroidEntryPoint
class SettingTabFragment : BaseFragment<FragmentSettingTabBinding>(R.layout.fragment_setting_tab),
    RootTabFragment {

    private val viewModel by viewModelOf<SettingTabViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        binding.button1.setOnClickListener {
            ServiceTermsActivity.start(requireContext())
        }
        binding.button2.setOnClickListener {
            PrivacyPolicyActivity.start(requireContext())
        }
    }

    override fun scrollToTop() {
        // TODO(yh): replace to scroll logic
        Toast.makeText(requireContext(), "Setting", Toast.LENGTH_SHORT).show()
    }
}