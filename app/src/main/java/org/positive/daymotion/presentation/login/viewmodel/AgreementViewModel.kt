package org.positive.daymotion.presentation.login.viewmodel

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.presentation.common.base.BaseViewModel
import org.positive.daymotion.presentation.common.util.liveDataMerge
import javax.inject.Inject

@HiltViewModel
class AgreementViewModel @Inject constructor() : BaseViewModel() {

    val isAgreedFirstRequiredTerms = MutableLiveData(false)

    val isAgreedSecondRequiredTerms = MutableLiveData(false)

    val isAgreedFirstNotRequiredTerms = MutableLiveData(false)

    val isEnabledGoNext = liveDataMerge(
        isAgreedFirstRequiredTerms,
        isAgreedSecondRequiredTerms
    ) { first, second -> first == true && second == true }

    fun changeAllAgreed(isChecked: Boolean) {
        isAgreedFirstRequiredTerms.value = isChecked
        isAgreedSecondRequiredTerms.value = isChecked
        isAgreedFirstNotRequiredTerms.value = isChecked
    }
}