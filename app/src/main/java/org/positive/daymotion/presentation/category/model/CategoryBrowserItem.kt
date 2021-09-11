package org.positive.daymotion.presentation.category.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import org.positive.daymotion.R
import org.positive.daymotion.domain.Category
import java.text.DecimalFormat
import kotlin.random.Random

data class CategoryBrowserItem(
    val id: String,
    val categoryName: String,
    val participants: Int
) {

    @get:DrawableRes
    val background = when (categoryName) {
        "일상", "상황", "표현", "상상" -> {
            when (RANDOM_SEED++.rem(4)) {
                0 -> R.drawable.img_category_01
                1 -> R.drawable.img_category_02
                2 -> R.drawable.img_category_03
                else -> R.drawable.img_category_04
            }
        }
        "회고", "취향", "인생팁", "명언", "표정" -> {
            R.drawable.background_category_browser_item
        }
        else -> R.color._F1F1F1
    }

    val isVisibleParticipants
        get() = categoryName == "일상" || categoryName == "상황" ||
                categoryName == "표현" || categoryName == "상상"

    @get:ColorRes
    val nameTextColorRes = if (isVisibleParticipants) {
        R.color._FFFFFF
    } else {
        R.color._000000
    }

    val nameTextAlpha = if (isVisibleParticipants) {
        1.0f
    } else {
        0.6f
    }

    val formattedParticipants = "# ${decimalFormatter.format(participants)}"

    companion object {
        private var RANDOM_SEED = Random.nextInt(0, 3)
        private val decimalFormatter = DecimalFormat("###,###")

        fun of(category: Category): CategoryBrowserItem {
            return CategoryBrowserItem(
                category.id,
                category.name,
                category.feedElements
            )
        }
    }
}