package org.positive.sms.extension

import androidx.annotation.MainThread
import org.positive.sms.presentation.base.BaseViewModel
import org.positive.sms.presentation.base.BaseActivity

@MainThread
inline fun <reified VM : BaseViewModel> BaseActivity<*>.viewModelOf() = getViewModelLazy(VM::class)