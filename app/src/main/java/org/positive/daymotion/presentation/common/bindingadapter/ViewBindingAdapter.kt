package org.positive.daymotion.presentation.common.bindingadapter

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter


@BindingAdapter("isVisible")
fun View.isVisibleBindingAdapter(isVisible: Boolean) {
    this.isVisible = isVisible
}

@BindingAdapter("isInvisible")
fun View.isInvisibleBindingAdapter(isInvisible: Boolean) {
    this.isInvisible = isInvisible
}

@BindingAdapter("constraintDimensionRatio")
fun View.setConstraintDimensionRatioBindingAdapter(ratio: String) {
    if (parent is ConstraintLayout) {
        val layoutParams = layoutParams as ConstraintLayout.LayoutParams
        layoutParams.dimensionRatio = ratio
        setLayoutParams(layoutParams)
    }
}