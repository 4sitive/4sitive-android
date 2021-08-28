package org.positive.daymotion.presentation.category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.data.repository.CategoryRepository
import org.positive.daymotion.presentation.category.model.CategoryBrowserItem
import org.positive.daymotion.presentation.common.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryBrowserPageViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : BaseViewModel() {

    private val _categoryBrowserItems = MutableLiveData<List<CategoryBrowserItem>>()
    val categoryBrowserItems: LiveData<List<CategoryBrowserItem>> get() = _categoryBrowserItems

    fun loadMissionHistories() {
        categoryRepository.getCategories()
            .apiLoadingCompose()
            .autoDispose {
                success { result ->
                    _categoryBrowserItems.value = result.map { CategoryBrowserItem.of(it) }
                }
                error {
                    showErrorMessage(it.message.orEmpty())
                }
            }

    }
}