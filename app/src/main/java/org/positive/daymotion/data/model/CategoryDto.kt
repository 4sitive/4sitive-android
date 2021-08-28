package org.positive.daymotion.data.model

data class GetCategoriesResponse(
    val content: List<Category>
) {
    data class Category(
        val id: String,
        val name: String,
        val feedElements: Int
    )
}