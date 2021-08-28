package org.positive.daymotion.presentation.common.base

import android.content.Context
import androidx.appcompat.app.AlertDialog
import org.positive.daymotion.R

class LoadingHandler(context: Context) {

    private var loadingCount = 0

    private val loadingDialog: AlertDialog by lazy {
        AlertDialog.Builder(context, R.style.LoadingDialog)
            .setCancelable(false)
            .setView(R.layout.widget_progress_bar)
            .create()
    }

    fun updateLoadingCount(loadingCount: Int) {
        this.loadingCount = loadingCount
        if (loadingCount > 0) {
            show()
        } else {
            hide()
        }
    }

    private fun show() {
        if (!loadingDialog.isShowing) {
            loadingDialog.show()
        }
    }

    private fun hide() {
        if (loadingCount == 0) {
            loadingDialog.hide()
        }
    }
}