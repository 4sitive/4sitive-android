package org.positive.daymotion.presentation.base.util

import org.positive.daymotion.presentation.base.BaseActivity
import org.positive.daymotion.presentation.base.BaseFragment

val BaseFragment<*>.requireBaseActivity: BaseActivity<*>
    get() = activity as BaseActivity<*>

val BaseFragment<*>.baseActivity: BaseActivity<*>?
    get() = when (val parentActivity = activity) {
        null -> null
        is BaseActivity<*> -> parentActivity
        else -> null
    }