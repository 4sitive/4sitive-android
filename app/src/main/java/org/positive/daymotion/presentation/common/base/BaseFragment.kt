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
import androidx.lifecycle.Observer

abstract class BaseFragment<B : ViewDataBinding>(
    @LayoutRes private val layoutId: Int
) : Fragment(), LiveDataObservable {

    protected lateinit var binding: B
        private set

    private val loadingObserver = Observer<Boolean> {
        showLoading(it)
    }

    private var viewModel: BaseViewModel? = null

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

    override fun onDestroy() {
        viewModel?.loadingCount?.removeObserver(loadingObserver)
        super.onDestroy()
    }

    fun observeBaseLiveData(viewModel: BaseViewModel) {
        this.viewModel = viewModel
        with(viewModel) {
            loadingCount.observeForever(loadingObserver::onChanged)
            showErrorMessageEvent.observeNonNull {
                showErrorMessage(it)
            }
        }
    }

    fun showLoading(isLoading: Boolean) {
        baseActivity?.showLoading(isLoading)
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