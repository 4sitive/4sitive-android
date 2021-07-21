package org.positive.daymotion.presentation.upload.view

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import androidx.appcompat.widget.AppCompatEditText

class BackButtonCallbackEditText : AppCompatEditText {

    var onPressBackButton: () -> Boolean = { false }

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

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK && onPressBackButton()) {
            true
        } else {
            super.onKeyPreIme(keyCode, event)
        }
    }
}