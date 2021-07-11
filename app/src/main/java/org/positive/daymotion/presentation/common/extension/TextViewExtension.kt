package org.positive.daymotion.presentation.common.extension

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.TypefaceSpan
import android.view.View
import android.widget.TextView
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat

fun TextView.applyClickableSpan(
    word: String,
    onClick: (View) -> Unit
) {
    movementMethod = LinkMovementMethod.getInstance()
    val spannable = SpannableString(text)
    val start: Int = text.indexOf(word)
    val end: Int = start + word.length
    spannable.setSpan(object : ClickableSpan() {
        override fun onClick(widget: View) {
            onClick(widget)
        }

        override fun updateDrawState(ds: TextPaint) {

        }
    }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    text = spannable
}

fun TextView.applyTypefaceSpan(
    word: String,
    @FontRes fontId: Int,
    style: Int = Typeface.NORMAL
) {
    val spannable = SpannableString(text)
    val start: Int = text.indexOf(word)
    val end: Int = start + word.length
    val font = ResourcesCompat.getFont(context, fontId)
    spannable.setSpan(object : TypefaceSpan(null) {
        override fun updateDrawState(ds: TextPaint) {
            ds.typeface = Typeface.create(font, style)
        }
    }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    text = spannable
}