package org.positive.daymotion.presentation.common.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner

abstract class BaseActivity<B : ViewDataBinding>(
    @LayoutRes private val layoutId: Int
) : AppCompatActivity(), LiveDataObservable {

    protected val binding: B by lazy { DataBindingUtil.setContentView(this, layoutId) }

    private val loadingHandler by lazy { LoadingHandler(this) }

    override val lifecycleOwner: LifecycleOwner
        get() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
    }

    fun observeBaseLiveData(viewModel: BaseViewModel) {
        with(viewModel) {
            loadingCount.observeNonNull {
                updateLoadingCount(it)
            }
            showErrorMessageEvent.observeNonNull {
                showErrorMessage(it)
            }
        }
    }

    fun updateLoadingCount(loadingCount: Int) {
        loadingHandler.updateLoadingCount(loadingCount)
    }

    fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}