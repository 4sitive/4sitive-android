package org.positive.daymotion.presentation.category.model


data class CategoryDetailItem(
    val imageUrl: String,
    val imageType: ImageType
) {

    val imageRatio = if (imageType == ImageType.PORTRAIT) "155:234" else "155:117"

    enum class ImageType {
        PORTRAIT, LANDSCAPE
    }
}