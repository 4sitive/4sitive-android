package org.positive.daymotion.presentation.root

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityRootBinding
import org.positive.daymotion.extension.startOnTop
import org.positive.daymotion.presentation.base.BaseActivity
import org.positive.daymotion.presentation.base.util.viewModelOf

@AndroidEntryPoint
class RootActivity : BaseActivity<ActivityRootBinding>(R.layout.activity_root) {

    private val viewModel by viewModelOf<RootViewModel>()
    private var backWait: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

//        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
//        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
//            val tab = when (item.itemId) {
//                R.id.menu_home -> HomeTabFragment()
//                R.id.menu_category -> CategoryTabFragment()
//                R.id.menu_my -> MyTabFragment()
//                R.id.menu_setting -> SettingTabFragment()
//                else -> throw IllegalStateException("invalid bottom nav menu id")
//            }
//            supportFragmentManager.commit {
//                replace(R.id.container, tab)
//            }
//            true
//        }
//        supportFragmentManager.commit {
//            replace(R.id.container, HomeTabFragment())
//        }
        setupObserver()
    }
    
    override fun onBackPressed() {
        viewModel.backToPreviousTab()
    }

    private fun setupObserver() {
        with(viewModel) {
            currentTab.observeNonNull {
                // TODO(yh): change tab
            }
            alreadySelectedTab.observeNonNull {
                // TODO(yh): refresh tab
            }
            emptyBackStack.observe {
                appFinish()
            }
        }
    }

    private fun appFinish() {
        if (System.currentTimeMillis() - backWait >= 2000) {
            backWait = System.currentTimeMillis()
            Toast.makeText(this, "뒤로 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        } else {
            finish()
        }
    }

    companion object {
        fun startOnTop(context: Context) = context.startOnTop<RootActivity>()
    }
}