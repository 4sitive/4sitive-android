package org.positive.daymotion.presentation.start

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.BuildConfig
import org.positive.daymotion.R
import org.positive.daymotion.common.showPopupDialog
import org.positive.daymotion.databinding.ActivityStartBinding
import org.positive.daymotion.extension.viewModelOf
import org.positive.daymotion.presentation.base.BaseActivity
import org.positive.daymotion.presentation.home.HomeActivity
import org.positive.daymotion.presentation.login.LoginActivity

@AndroidEntryPoint
class StartActivity : BaseActivity<ActivityStartBinding>(R.layout.activity_start) {

    private val viewModel by viewModelOf<StartViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.checkVersionAndToken(BuildConfig.VERSION_NAME)

        viewModel.needUpdate.observe {
            showPopupDialog {
                title = "새로운 버전 업데이트가 필요 합니다."
                content = "..."
                isCancelable = false
                isVisibleCancelButton = false
                onConfirm {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(PLAY_STORE_URL)).apply {
                        setPackage("com.android.vending")
                    }
                    startActivity(intent)
                    finishAffinity()
                }
            }
        }

        viewModel.goToHome.observe {
            LoginActivity.startOnTop(this)
        }

        viewModel.goToLogin.observe {
            HomeActivity.startOnTop(this)
        }
    }

    companion object {
        private const val PLAY_STORE_URL =
            "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID
    }
}