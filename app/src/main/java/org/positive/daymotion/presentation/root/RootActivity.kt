package org.positive.daymotion.presentation.root

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityRootBinding
import org.positive.daymotion.extension.startOnTop
import org.positive.daymotion.presentation.base.BaseActivity
import org.positive.daymotion.presentation.base.util.viewModelOf

@AndroidEntryPoint
class RootActivity : BaseActivity<ActivityRootBinding>(R.layout.activity_root) {

    private val viewModel by viewModelOf<RootViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            // TODO(je): create fragment and setting listener
            true
        }
    }

    companion object {
        fun startOnTop(context: Context) = context.startOnTop<RootActivity>()
    }
}