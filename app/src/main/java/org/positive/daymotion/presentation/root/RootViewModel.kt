package org.positive.daymotion.presentation.root

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.common.SingleLiveEvent
import org.positive.daymotion.presentation.base.BaseViewModel
import org.positive.daymotion.presentation.root.model.Tab
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor() : BaseViewModel() {

    private val _currentTab = MutableLiveData(Tab.HOME)

    private val _changeTab = SingleLiveEvent<Pair<Tab, Tab>>()
    val changeTab: LiveData<Pair<Tab, Tab>> get() = _changeTab

    private val _alreadySelectedTab = SingleLiveEvent<Tab>()
    val alreadySelectedTab: LiveData<Tab> get() = _alreadySelectedTab

    private val _emptyBackStack = SingleLiveEvent<Nothing>()
    val emptyBackStack: LiveData<Nothing> get() = _emptyBackStack

    val isSelectedHomeTab: LiveData<Boolean> =
        Transformations.map(_currentTab) { it == Tab.HOME }

    val isSelectedCategoryTab: LiveData<Boolean> =
        Transformations.map(_currentTab) { it == Tab.CATEGORY }

    val isSelectedMyTab: LiveData<Boolean> =
        Transformations.map(_currentTab) { it == Tab.MY }

    val isSelectedSettingTab: LiveData<Boolean> =
        Transformations.map(_currentTab) { it == Tab.SETTING }

    private val backStack = BackStack()

    fun selectTab(selectedTab: Tab) {
        val currentTab = _currentTab.value ?: return

        if (currentTab == selectedTab) {
            _alreadySelectedTab.value = selectedTab
        } else {
            changeTab(currentTab, selectedTab)
            backStack.push(currentTab)
        }
    }

    fun backToPreviousTab() {
        val currentTab = _currentTab.value ?: return
        val previousTab = backStack.pop()

        if (previousTab == null) {
            _emptyBackStack.call()
        } else {
            changeTab(currentTab, previousTab)
            _currentTab.value = previousTab
        }
    }

    private fun changeTab(old: Tab, new: Tab) {
        _currentTab.value = new
        _changeTab.value = old to new
    }

    private class BackStack {
        private val backStack = mutableListOf<Tab>()

        fun push(tab: Tab) {
            backStack.remove(tab)
            backStack.add(tab)
            if (backStack.size == Tab.values().size) {
                backStack.removeFirst()
            }
        }

        fun pop(): Tab? {
            return backStack.removeLastOrNull()
        }
    }
}