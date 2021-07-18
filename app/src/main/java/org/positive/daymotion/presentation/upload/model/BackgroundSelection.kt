package org.positive.daymotion.presentation.upload.model

import android.graphics.drawable.Drawable

sealed class BackgroundSelection {

    data class Camera(
        var capturedUrl: String? = null
    ) : BackgroundSelection()

    data class Default(
        val defaultBackground: Drawable
    ) : BackgroundSelection()
}


