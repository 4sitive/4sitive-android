package org.positive.daymotion.presentation.common.bindingadapter

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.StateListDrawable
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import org.positive.daymotion.R


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

@BindingAdapter("pressEffect")
fun View.setPressEffect(withGaryBg: Boolean) {
    val stateListDrawable = StateListDrawable().apply {
        setExitFadeDuration(300)
        val pressedColor = if (withGaryBg) R.color._80000000 else R.color._80FFFFFF
        val colorDrawable = ColorDrawable(ContextCompat.getColor(context, pressedColor))
        addState(intArrayOf(android.R.attr.state_pressed), colorDrawable)
    }
    foreground = stateListDrawable
}