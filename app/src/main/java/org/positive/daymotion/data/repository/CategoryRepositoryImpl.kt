package org.positive.daymotion.data.repository

import io.reactivex.rxjava3.core.Single
import org.positive.daymotion.data.api.CategoryApi
import org.positive.daymotion.domain.Category
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryApi: CategoryApi
) : CategoryRepository {

    override fun getCategories(): Single<List<Category>> {
        return categoryApi.getCategories().map {
            it.content.map { category ->
                Category(category.id, category.name, category.feedElements)
            }
        }
    }
}