package org.positive.daymotion.presentation.upload.view

import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

class DragTouchListener : View.OnTouchListener {
    private var isDragged = false

    private var offsetX = 0f
    private var offsetY = 0f
    private var previousX = 0f
    private var previousY = 0f
    private var moved = 0f

    override fun onTouch(
        v: View,
        event: MotionEvent
    ) = when (event.action) {
        MotionEvent.ACTION_DOWN -> {
            offsetX = v.x
            offsetY = v.y
            previousX = event.rawX
            previousY = event.rawY
            false
        }
        MotionEvent.ACTION_MOVE -> {
            moved += abs(event.rawX - previousX) + abs(event.rawY - previousY)
            if (moved > 20) {
                isDragged = true
                v.animate()
                    .x(event.rawX - previousX + offsetX)
                    .y(event.rawY - previousY + offsetY)
                    .setDuration(0)
                    .start()
                true
            } else {
                false
            }
        }
        MotionEvent.ACTION_UP -> {
            if (isDragged) {
                isDragged = false
                moved = 0f
                true
            } else {
                false
            }
        }
        else -> false
    }
}