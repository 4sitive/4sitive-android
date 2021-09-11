package org.positive.daymotion.presentation.home.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.data.repository.FeedRepository
import org.positive.daymotion.presentation.common.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AddEmojiViewModel @Inject constructor(
    private val feedRepository: FeedRepository
) : BaseViewModel() {

}