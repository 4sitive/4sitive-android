package org.positive.daymotion.extension

import androidx.annotation.MainThread
import org.positive.daymotion.presentation.base.BaseActivity
import org.positive.daymotion.presentation.base.BaseFragment
import org.positive.daymotion.presentation.base.BaseViewModel

@MainThread
inline fun <reified VM : BaseViewModel> BaseFragment<*>.viewModelOf() = getViewModelLazy(VM::class)

@MainThread
inline fun <reified VM : BaseViewModel> BaseFragment<*>.sharedViewModelOf(): Lazy<VM> {
    val parentActivity = activity
    require(parentActivity is BaseActivity<*>)
    return getViewModelLazy(VM::class, parentActivity.viewModelStore)
}