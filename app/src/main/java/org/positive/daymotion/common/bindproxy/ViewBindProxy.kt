package org.positive.daymotion.common.bindproxy

import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@set:BindingAdapter("isVisible")
var View.isVisibleProxy: Boolean
    get() = isVisible
    set(value) {
        isVisible = value
    }

@set:BindingAdapter("isInvisible")
var View.isInvisibleProxy: Boolean
    get() = isInvisible
    set(value) {
        isInvisible = value
    }
