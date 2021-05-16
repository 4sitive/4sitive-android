package org.positive.sms.presentation.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import android.view.KeyEvent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import org.positive.sms.common.PsConstants

class PsWebView : WebView {

    constructor(
        context: Context
    ) : super(context)

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    init {
        initWebView()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && canGoBack()) {
            goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun initWebView() {
        webViewClient = PsWebViewClient()
        settings.apply {
            @SuppressLint("SetJavaScriptEnabled")
            javaScriptEnabled = true
            textZoom = 100
        }
    }

    private inner class PsWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return if (request?.url != null && isPositiveScheme(request.url)) {
                true
            } else {
                super.shouldOverrideUrlLoading(view, request)
            }
        }

        private fun isPositiveScheme(url: Uri): Boolean {
            if (url.scheme == PsConstants.APP_SCHEME) {
                context.startActivity(Intent(Intent.ACTION_VIEW, url))
                return true
            }
            return false
        }
    }
}