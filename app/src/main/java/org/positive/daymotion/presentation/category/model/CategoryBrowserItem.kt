package org.positive.daymotion.presentation.category.model

data class CategoryBrowserItem(
    val categoryName: String,
    val participants: Int
) {
    val isSmallType = participants < 100

    val formattedParticipants = "#$participants"
}