package org.positive.daymotion.presentation.category.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import org.positive.daymotion.R
import java.text.DecimalFormat
import kotlin.random.Random

data class CategoryBrowserItem(
    val categoryName: String,
    val participants: Int
) {

    @get:DrawableRes
    val background = when {
        participants > 100 -> {
            when (RANDOM_SEED++.rem(3)) {
                0 -> R.drawable.img_category_01
                1 -> R.drawable.img_category_02
                else -> R.drawable.img_category_03
            }
        }
        participants in 11..100 -> R.drawable.background_category_browser_item
        else -> R.color._F1F1F1
    }

    val isVisibleParticipants = participants > 100

    @get:ColorRes
    val nameTextColorRes = if (participants > 100) {
        R.color._FFFFFF
    } else {
        R.color._000000
    }

    val nameTextAlpha = if (participants > 100) {
        1.0f
    } else {
        0.6f
    }

    val formattedParticipants = "# ${decimalFormatter.format(participants)}"

    companion object {
        private var RANDOM_SEED = Random.nextInt(0, 2)
        private val decimalFormatter = DecimalFormat("###,###")
    }
}