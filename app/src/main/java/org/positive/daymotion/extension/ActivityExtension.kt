package org.positive.daymotion.extension

import androidx.annotation.MainThread
import org.positive.daymotion.presentation.base.BaseViewModel
import org.positive.daymotion.presentation.base.BaseActivity

@MainThread
inline fun <reified VM : BaseViewModel> BaseActivity<*>.viewModelOf() = getViewModelLazy(VM::class)