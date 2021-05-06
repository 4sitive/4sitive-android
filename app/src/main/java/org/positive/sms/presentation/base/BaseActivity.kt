package org.positive.sms.presentation.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import org.positive.sms.common.LoadingHandler
import org.positive.sms.common.ViewModelHolder
import kotlin.reflect.KClass

abstract class BaseActivity<B : ViewDataBinding>(
    @LayoutRes private val layoutId: Int
) : AppCompatActivity() {

    protected val binding: B by lazy { DataBindingUtil.setContentView(this, layoutId) }

    private val loadingHandler by lazy { LoadingHandler(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
    }

    fun <VM : BaseViewModel> getViewModelLazy(
        viewModelClass: KClass<VM>
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

    protected fun <T> LiveData<T>.observe(onChange: (T?) -> Unit) {
        this.observe(this@BaseActivity, onChange)
    }

    protected fun <T> LiveData<T>.observeNonNull(onChange: (T) -> Unit) {
        this.observe(this@BaseActivity) {
            it?.let { onChange(it) }
        }
    }

    private fun showLoadingDialog(isLoading: Boolean?) {
        if (isLoading == true) {
            loadingHandler.show()
        } else {
            loadingHandler.hide()
        }
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}