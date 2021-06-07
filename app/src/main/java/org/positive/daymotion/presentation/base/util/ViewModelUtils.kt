package org.positive.daymotion.presentation.base.util

import androidx.annotation.MainThread
import org.positive.daymotion.presentation.base.BaseActivity
import org.positive.daymotion.presentation.base.BaseFragment
import org.positive.daymotion.presentation.base.BaseViewModel

@MainThread
inline fun <reified VM : BaseViewModel> BaseActivity<*>.viewModelOf() = ViewModelHolder(
    VM::class,
    { viewModelStore },
    { defaultViewModelProviderFactory },
    { observeBaseLiveData(it) }
)

@MainThread
inline fun <reified VM : BaseViewModel> BaseFragment<*>.viewModelOf() = ViewModelHolder(
    VM::class,
    { viewModelStore },
    { defaultViewModelProviderFactory },
    { observeBaseLiveData(it) }
)

@MainThread
inline fun <reified VM : BaseViewModel> BaseFragment<*>.sharedViewModelOf() = ViewModelHolder(
    VM::class,
    { requireBaseActivity.viewModelStore },
    { defaultViewModelProviderFactory },
    { observeBaseLiveData(it) }
)