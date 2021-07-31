package org.positive.daymotion.presentation.root

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityRootBinding
import org.positive.daymotion.presentation.common.ScrollableFragment
import org.positive.daymotion.presentation.common.base.BaseActivity
import org.positive.daymotion.presentation.common.base.viewModelOf
import org.positive.daymotion.presentation.common.extension.startOnTop
import org.positive.daymotion.presentation.home.fragment.HomeTabFragment
import org.positive.daymotion.presentation.root.model.Tab

@AndroidEntryPoint
class RootActivity : BaseActivity<ActivityRootBinding>(R.layout.activity_root) {

    private val viewModel by viewModelOf<RootViewModel>()
    private var backWait: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

        setupViews()
        setupObservers()
    }

    override fun onBackPressed() {
        viewModel.backToPreviousTab()
    }

    private fun setupViews() {
        supportFragmentManager.commit {
            add(R.id.container, HomeTabFragment::class.java, null)
        }
    }

    private fun setupObservers() {
        with(viewModel) {
            changeTab.observeNonNull { (old, new) ->
                replaceTab(old, new)
            }
            alreadySelectedTab.observeNonNull { tab ->
                supportFragmentManager.fragments
                    .filterIsInstance(ScrollableFragment::class.java)
                    .find { tab.clazz.isInstance(it) }
                    ?.scrollToTop()
            }
            emptyBackStack.observe {
                appFinish()
            }
        }
    }

    private fun replaceTab(old: Tab?, new: Tab) {
        val newTabFragment = supportFragmentManager.fragments
            .find { new.clazz.isInstance(it) }

        val oldTabFragment = supportFragmentManager.fragments
            .find { old?.clazz?.isInstance(it) ?: false }

        supportFragmentManager.commit {
            if (oldTabFragment != null) {
                hide(oldTabFragment)
            }

            if (newTabFragment == null) {
                add(R.id.container, new.clazz.java, null)
            } else {
                show(newTabFragment)
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