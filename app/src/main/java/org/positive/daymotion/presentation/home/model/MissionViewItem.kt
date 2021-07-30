package org.positive.daymotion.presentation.home.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class MissionViewItem(
    val mission: String,
    @DrawableRes val background: Int,
    @DrawableRes val effect: Int,
) : Parcelable