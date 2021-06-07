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
import org.positive.daymotion.presentation.base.util.LiveDataObservable
import org.positive.daymotion.presentation.base.util.baseActivity

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
            isLoading.observe {
                showLoadingDialog(it)
            }
            showErrorMessageEvent.observeNonNull {
                showErrorMessage(it)
            }
        }
    }

    fun showLoadingDialog(isLoading: Boolean?) {
        baseActivity?.showLoadingDialog(isLoading)
    }

    fun showErrorMessage(message: String) {
        baseActivity?.showErrorMessage(message)
    }
}