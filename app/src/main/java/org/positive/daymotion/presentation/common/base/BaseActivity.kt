package org.positive.daymotion.presentation.common.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

abstract class BaseActivity<B : ViewDataBinding>(
    @LayoutRes private val layoutId: Int
) : AppCompatActivity(), LiveDataObservable {

    protected val binding: B by lazy { DataBindingUtil.setContentView(this, layoutId) }

    private val loadingHandler by lazy { LoadingHandler(this) }

    private val loadingObserver = Observer<Boolean> {
        showLoading(it)
    }

    private var viewModel: BaseViewModel? = null

    override val lifecycleOwner: LifecycleOwner
        get() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
    }

    override fun onDestroy() {
        viewModel?.loadingCount?.removeObserver(loadingObserver)
        loadingHandler.clear()
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
        loadingHandler.updateLoadingCount(isLoading)
    }

    fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}