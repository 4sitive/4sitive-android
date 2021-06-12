package org.positive.daymotion.presentation.category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.presentation.base.BaseViewModel
import org.positive.daymotion.presentation.category.model.CategoryBrowserItem
import javax.inject.Inject

@HiltViewModel
class CategoryBrowserPageViewModel @Inject constructor() : BaseViewModel() {

    private val _categoryBrowserItems = MutableLiveData<List<CategoryBrowserItem>>()
    val categoryBrowserItems: LiveData<List<CategoryBrowserItem>> get() = _categoryBrowserItems

    fun loadMissionHistories() {
        val items = buildList {
            add(CategoryBrowserItem(categoryName = "취미", participants = 200))
            add(CategoryBrowserItem(categoryName = "개인", participants = 10))
            add(CategoryBrowserItem(categoryName = "유머", participants = 12))
            add(CategoryBrowserItem(categoryName = "날씨", participants = 13))
            add(CategoryBrowserItem(categoryName = "친구", participants = 500))
            add(CategoryBrowserItem(categoryName = "음악", participants = 600))
        }

        _categoryBrowserItems.value = items
    }
}