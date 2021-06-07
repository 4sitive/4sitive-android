package org.positive.daymotion.presentation.root

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityRootBinding
import org.positive.daymotion.extension.startOnTop
import org.positive.daymotion.presentation.base.BaseActivity
import org.positive.daymotion.presentation.base.util.viewModelOf
import org.positive.daymotion.presentation.root.tabs.category.CategoryTabFragment
import org.positive.daymotion.presentation.root.tabs.home.HomeTabFragment
import org.positive.daymotion.presentation.root.tabs.my.MyTabFragment
import org.positive.daymotion.presentation.root.tabs.setting.SettingTabFragment

@AndroidEntryPoint
class RootActivity : BaseActivity<ActivityRootBinding>(R.layout.activity_root) {

    private val viewModel by viewModelOf<RootViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            val tab = when (item.itemId) {
                R.id.menu_home -> HomeTabFragment()
                R.id.menu_category -> CategoryTabFragment()
                R.id.menu_my -> MyTabFragment()
                R.id.menu_setting -> SettingTabFragment()
                else -> throw IllegalStateException("invalid bottom nav menu id")
            }
            supportFragmentManager.commit {
                replace(R.id.container, tab)
            }
            true
        }
        supportFragmentManager.commit {
            replace(R.id.container, HomeTabFragment())
        }
    }

    companion object {
        fun startOnTop(context: Context) = context.startOnTop<RootActivity>()
    }
}