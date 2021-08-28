package org.positive.daymotion.data.repository

import io.reactivex.rxjava3.core.Single
import org.positive.daymotion.domain.Category

interface CategoryRepository {

    fun getCategories(): Single<List<Category>>
}