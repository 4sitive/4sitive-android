package org.positive.daymotion.presentation.setting

import android.os.Bundle
import android.view.View
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.FragmentSettingTabBinding
import org.positive.daymotion.presentation.common.base.BaseFragment
import org.positive.daymotion.presentation.common.base.viewModelOf
import org.positive.daymotion.presentation.common.showPopupDialog
import org.positive.daymotion.presentation.login.LoginActivity

@AndroidEntryPoint
class SettingTabFragment : BaseFragment<FragmentSettingTabBinding>(R.layout.fragment_setting_tab) {

    private val viewModel by viewModelOf<SettingTabViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        binding.logoutTextView.setOnClickListener {
            // TODO(je): logout api
            val logoutMsg = requireContext().resources.getString(R.string.logoutMsg)
            Toast.makeText(requireContext(), logoutMsg, Toast.LENGTH_SHORT).show()
            LoginActivity.startOnTop(requireContext())
        }

        binding.pushSwitch.setOnCheckedChangeListener { _, isChecked ->
            onPushAlarm(isChecked)
        }

        binding.serviceButton.setOnClickListener {
            ServiceTermsActivity.start(requireContext())
        }
        binding.privacyButton.setOnClickListener {
            PrivacyPolicyActivity.start(requireContext())
        }

        binding.secessionButton.setOnClickListener {
            showPopupDialog {
                title = "탈퇴하시려구요?"
                content = "서비스를 탈퇴하면 모든 데이터는 다 사라져요.\n" +
                        "공개되지 않은 미션이 아직 많~이 남았어요.\n" +
                        "다시 한번 생각해 주세요!"
                blueButtonText = "안할게요!"
                grayButtonText = "탈퇴할래요"
                isVisibleGrayButton = true
                isCancelable = true
                onClickGrayButton { setSecessionApi() }
            }
        }
    }

    private fun onPushAlarm(isChecked: Boolean) {
        if (isChecked) {
            Toast.makeText(requireContext(), "푸시 알림 ON", Toast.LENGTH_SHORT).show()
            // TODO(je): push alarm on
        } else {
            Toast.makeText(requireContext(), "푸시 알림 OFF", Toast.LENGTH_SHORT).show()
            // TODO(je): push alarm off
        }
    }

    private fun setSecessionApi() {
        // TODO(je): secession api
        Toast.makeText(requireContext(), "탈퇴가 완료되었습니다.", Toast.LENGTH_SHORT).show()
        LoginActivity.startOnTop(requireContext())
    }
}
