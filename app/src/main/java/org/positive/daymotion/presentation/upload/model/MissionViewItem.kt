package org.positive.daymotion.presentation.upload.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MissionViewItem(
    val id: String,
    val category: String,
    val content: String
) : Parcelable