package org.positive.daymotion.presentation.category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.presentation.base.BaseViewModel
import org.positive.daymotion.presentation.category.model.CategoryFilterItem
import javax.inject.Inject

@HiltViewModel
class CategoryFilterPageViewModel @Inject constructor() : BaseViewModel() {

    private val _categoryFilters = MutableLiveData<List<CategoryFilterItem>>()
    val categoryFilters: LiveData<List<CategoryFilterItem>> get() = _categoryFilters

    fun loadMissionHistories() {
        val items = buildList {
            add(CategoryFilterItem(categoryName = "취미", participants = 200))
            add(CategoryFilterItem(categoryName = "개인", participants = 10))
            add(CategoryFilterItem(categoryName = "유머", participants = 12))
            add(CategoryFilterItem(categoryName = "날씨", participants = 13))
            add(CategoryFilterItem(categoryName = "친구", participants = 500))
            add(CategoryFilterItem(categoryName = "음악", participants = 600))
        }

        _categoryFilters.value = items
    }
}