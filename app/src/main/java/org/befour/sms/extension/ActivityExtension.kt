package org.befour.sms.extension

import androidx.annotation.MainThread
import org.befour.sms.presentation.base.BaseViewModel
import org.befour.sms.presentation.base.BaseActivity

@MainThread
inline fun <reified VM : BaseViewModel> BaseActivity<*>.viewModelOf() = getViewModelLazy(VM::class)