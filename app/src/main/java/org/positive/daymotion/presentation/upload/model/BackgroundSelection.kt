package org.positive.daymotion.presentation.upload.model

import android.net.Uri
import androidx.annotation.DrawableRes

sealed class BackgroundSelection {

    object Camera : BackgroundSelection()

    data class Custom(val uri: Uri) : BackgroundSelection()

    data class Default(
        @DrawableRes val thumbnail: Int,
        @DrawableRes val background: Int
    ) : BackgroundSelection()
}


