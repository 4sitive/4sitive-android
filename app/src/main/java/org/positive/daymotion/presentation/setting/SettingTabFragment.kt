package org.positive.daymotion.presentation.setting

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.FragmentSettingTabBinding
import org.positive.daymotion.presentation.base.BaseFragment
import org.positive.daymotion.presentation.base.util.viewModelOf
import org.positive.daymotion.presentation.login.LoginActivity
import org.positive.daymotion.presentation.root.model.RootTabFragment

@AndroidEntryPoint
class SettingTabFragment : BaseFragment<FragmentSettingTabBinding>(R.layout.fragment_setting_tab),
        RootTabFragment {

    private val viewModel by viewModelOf<SettingTabViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        binding.logoutTextView.setOnClickListener() {
            // TODO(je): logout api
            val logoutMsg = requireContext().resources.getString(R.string.logoutMsg)
            Toast.makeText(requireContext(), logoutMsg, Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }

        binding.serviceButton.setOnClickListener {
            ServiceTermsActivity.start(requireContext())
        }
        binding.privacyButton.setOnClickListener {
            PrivacyPolicyActivity.start(requireContext())
        }
        binding.secessionButton.setOnClickListener {
            // TODO(je): Dialog & secession api
        }
    }

    override fun scrollToTop() {
        // TODO(yh): replace to scroll logic
        Toast.makeText(requireContext(), "Setting", Toast.LENGTH_SHORT).show()
    }
}