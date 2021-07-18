package org.positive.daymotion.presentation.upload.model

import androidx.annotation.DrawableRes

sealed class BackgroundSelection {

    data class Camera(
        var capturedUrl: String? = null
    ) : BackgroundSelection()

    data class Default(
        @DrawableRes val thumbnail: Int,
        @DrawableRes val background: Int
    ) : BackgroundSelection()
}


