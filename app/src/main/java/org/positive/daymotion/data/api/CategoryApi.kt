package org.positive.daymotion.data.api

import io.reactivex.rxjava3.core.Single
import org.positive.daymotion.data.model.GetCategoriesResponse
import retrofit2.http.GET

interface CategoryApi {

    @GET("/categories")
    fun getCategories(): Single<GetCategoriesResponse>
}