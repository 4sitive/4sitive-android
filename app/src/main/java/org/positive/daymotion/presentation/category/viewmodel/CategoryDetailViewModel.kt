package org.positive.daymotion.presentation.category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.presentation.base.BaseViewModel
import org.positive.daymotion.presentation.category.model.CategoryDetailItem
import javax.inject.Inject

@HiltViewModel
class CategoryDetailViewModel @Inject constructor() : BaseViewModel() {

    private val _categoryDetails = MutableLiveData<List<CategoryDetailItem>>()
    val categoryDetails: LiveData<List<CategoryDetailItem>> get() = _categoryDetails

    fun loadCategoryDetails() {
        _categoryDetails.value = buildList {
            add(CategoryDetailItem("", CategoryDetailItem.ImageType.PORTRAIT))
            add(CategoryDetailItem("", CategoryDetailItem.ImageType.LANDSCAPE))
            add(CategoryDetailItem("", CategoryDetailItem.ImageType.PORTRAIT))
            add(CategoryDetailItem("", CategoryDetailItem.ImageType.LANDSCAPE))
        }
    }
}