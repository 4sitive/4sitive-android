package org.positive.daymotion.common

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import org.positive.daymotion.presentation.base.BaseViewModel
import kotlin.reflect.KClass

class ViewModelHolder<VM : BaseViewModel>(
    private val viewModelClass: KClass<VM>,
    private val storeProducer: () -> ViewModelStore,
    private val factoryProducer: () -> ViewModelProvider.Factory,
    private val viewModelSetup: (VM) -> Unit
) : Lazy<VM> {
    private var _cached: VM? = null

    override val value: VM
        get() {
            val cached = _cached
            val viewModel = if (cached == null) {
                val store = storeProducer()
                val factory = factoryProducer()
                ViewModelProvider(store, factory).get(viewModelClass.java).also {
                    _cached = it
                }
            } else {
                cached
            }

            return viewModel.apply { viewModelSetup(this) }
        }

    override fun isInitialized() = _cached != null
}