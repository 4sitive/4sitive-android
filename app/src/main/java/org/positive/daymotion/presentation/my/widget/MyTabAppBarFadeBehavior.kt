package org.positive.daymotion.presentation.my.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

class MyTabAppBarFadeBehavior(
    context: Context,
    attrs: AttributeSet?
) : CoordinatorLayout.Behavior<View>(context, attrs) {
    private var appBarInitialHeight: Float? = null

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ) = dependency is AppBarLayout

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        val max = appBarInitialHeight ?: return false
        child.apply {
            alpha = calcRatioValue(dependency.y, max)
        }
        return true
    }

    private fun calcRatioValue(current: Float, max: Float): Float {
        return 1f - (current / max)
    }
}