package org.positive.daymotion.presentation.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.positive.daymotion.R
import org.positive.daymotion.presentation.common.base.BaseViewModel
import org.positive.daymotion.presentation.home.model.FeedViewItem
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor() : BaseViewModel() {

    private val _feeds = MutableLiveData<List<FeedViewItem>>()
    val feeds: LiveData<List<FeedViewItem>> get() = _feeds

    fun loadFeedList() {
        _feeds.value = listOf(
            FeedViewItem("피드1", R.drawable.img_tmp_feed1, false, "사용자1", emptyList()),
            FeedViewItem("피드2", R.drawable.img_tmp_feed2, true, "사용자2", emptyList()),
            FeedViewItem("피드3", R.drawable.img_tmp_feed3, false, "사용자3", emptyList()),
            FeedViewItem("피드4", R.drawable.img_tmp_feed1, true, "사용자4", emptyList()),
            FeedViewItem("피드5", R.drawable.img_tmp_feed2, false, "사용자5", emptyList()),
            FeedViewItem("피드6", R.drawable.img_tmp_feed3, false, "사용자6", emptyList()),
        )
    }
}