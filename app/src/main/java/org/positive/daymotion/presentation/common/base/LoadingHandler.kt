package org.positive.daymotion.presentation.common.base

import android.content.Context
import androidx.appcompat.app.AlertDialog
import org.positive.daymotion.R

class LoadingHandler(private val context: Context) {

    private var loadingCount = 0

    private val loadingDialog: AlertDialog by lazy {
        AlertDialog.Builder(context, R.style.LoadingDialog)
            .setCancelable(false)
            .setView(R.layout.widget_progress_bar)
            .create()
    }

    fun updateLoadingCount(isLoading: Boolean) {
        if (isLoading) {
            loadingCount++
        } else {
            loadingCount--
        }

        if (loadingCount > 0) {
            if (!loadingDialog.isShowing) {
                loadingDialog.show()
            }
        } else {
            loadingCount = 0
            if (loadingDialog.isShowing) {
                loadingDialog.dismiss()
            }
        }
    }

    fun clear() {
        loadingDialog.dismiss()
    }
}