package org.positive.daymotion.presentation.common.bindingadapter

import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

@BindingAdapter("startDrawable")
fun TextView.setStartDrawable(@DrawableRes resourceId: Int) {
    setCompoundDrawablesWithIntrinsicBounds(resourceId, 0, 0, 0)
}