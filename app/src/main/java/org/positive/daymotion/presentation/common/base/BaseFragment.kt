package org.positive.daymotion.presentation.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner

abstract class BaseFragment<B : ViewDataBinding>(
    @LayoutRes private val layoutId: Int
) : Fragment(), LiveDataObservable {

    protected lateinit var binding: B
        private set

    override val lifecycleOwner: LifecycleOwner
        get() = this

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    fun observeBaseLiveData(viewModel: BaseViewModel) {
        with(viewModel) {
            loadingCount.observeNonNull {
                baseActivity?.updateLoadingCount(it)
            }
            showErrorMessageEvent.observeNonNull {
                showErrorMessage(it)
            }
        }
    }

    fun showErrorMessage(message: String) {
        baseActivity?.showErrorMessage(message)
    }
}

val BaseFragment<*>.requireBaseActivity: BaseActivity<*>
    get() = activity as BaseActivity<*>

val BaseFragment<*>.baseActivity: BaseActivity<*>?
    get() = when (val parentActivity = activity) {
        null -> null
        is BaseActivity<*> -> parentActivity
        else -> null
    }