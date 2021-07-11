package org.positive.daymotion.presentation.common.bindingadapter

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.view.View
import androidx.annotation.ColorInt
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

@BindingAdapter("useWhitePressEffect")
fun View.setWhitePressEffect(usePressEffect: Boolean?) {
    if (usePressEffect == true) {
        foreground =
            createPressEffectDrawable(ContextCompat.getColor(context, R.color._80FFFFFF), null)
    }
}

@BindingAdapter("useGrayPressEffect")
fun View.setGrayPressEffect(usePressEffect: Boolean?) {
    if (usePressEffect == true) {
        foreground =
            createPressEffectDrawable(ContextCompat.getColor(context, R.color._80000000), null)
    }
}

@BindingAdapter("useWhitePressEffect", "pressEffectRadius")
fun View.setWhitePressEffectWithRadius(usePressEffect: Boolean?, radius: Float?) {
    if (usePressEffect == true) {
        foreground =
            createPressEffectDrawable(ContextCompat.getColor(context, R.color._80FFFFFF), radius)
    }
}

@BindingAdapter("useGrayPressEffect", "pressEffectRadius")
fun View.setGrayPressEffectWithRadius(usePressEffect: Boolean?, radius: Float?) {
    if (usePressEffect == true) {
        foreground =
            createPressEffectDrawable(ContextCompat.getColor(context, R.color._80000000), radius)
    }
}

private fun createPressEffectDrawable(
    @ColorInt color: Int,
    radius: Float?
): Drawable = if (radius == null) {
    StateListDrawable().apply {
        setExitFadeDuration(300)
        addState(intArrayOf(android.R.attr.state_pressed), ColorDrawable(color))
    }
} else {
    StateListDrawable().apply {
        setExitFadeDuration(300)
        val gradientDrawable = GradientDrawable().apply {
            setColor(color)
            cornerRadii = FloatArray(8) { radius }
        }
        addState(intArrayOf(android.R.attr.state_pressed), gradientDrawable)
    }
}