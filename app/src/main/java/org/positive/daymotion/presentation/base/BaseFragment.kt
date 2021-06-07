package org.positive.daymotion.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelStore
import org.positive.daymotion.presentation.base.util.ViewModelHolder
import kotlin.reflect.KClass

abstract class BaseFragment<B : ViewDataBinding>(
    @LayoutRes private val layoutId: Int
) : Fragment() {

    protected lateinit var binding: B
        private set

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

    protected fun <T> LiveData<T>.observe(onChange: (T?) -> Unit) {
        this.observe(viewLifecycleOwner, onChange)
    }

    protected fun <T> LiveData<T>.observeNonNull(onChange: (T) -> Unit) {
        this.observe(viewLifecycleOwner) {
            it?.let { onChange(it) }
        }
    }

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