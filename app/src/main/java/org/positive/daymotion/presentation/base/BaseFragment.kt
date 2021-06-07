package org.positive.daymotion.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStore
import org.positive.daymotion.presentation.base.util.ViewModelHolder
import kotlin.reflect.KClass

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

    fun <VM : BaseViewModel> getViewModelLazy(
        viewModelClass: KClass<VM>,
        viewModelStore: ViewModelStore = this.viewModelStore
    ): Lazy<VM> = ViewModelHolder(
        viewModelClass,
        { viewModelStore },
        { defaultViewModelProviderFactory },
        { observingBaseViewModel(it) }
    )

    private fun observingBaseViewModel(viewModel: BaseViewModel) {
        with(viewModel) {
            isLoading.observe {
                showLoadingDialog(it)
            }
            showErrorMessageEvent.observeNonNull {
                showErrorMessage(it)
            }
        }
    }

    fun showLoadingDialog(isLoading: Boolean?) {
        val parentActivity = activity
        if (parentActivity is BaseActivity<*>) {
            parentActivity.showLoadingDialog(isLoading)
        }
    }

    fun showErrorMessage(message: String) {
        val parentActivity = activity
        if (parentActivity is BaseActivity<*>) {
            parentActivity.showErrorMessage(message)
        }
    }
}