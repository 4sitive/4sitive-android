package org.positive.daymotion.presentation.category.activity

import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ActivityCategoryDetailBinding
import org.positive.daymotion.presentation.category.model.DetailQueryType
import org.positive.daymotion.presentation.category.viewmodel.CategoryDetailViewModel
import org.positive.daymotion.presentation.common.adapter.FeedThumbnailAdapter
import org.positive.daymotion.presentation.common.base.BaseActivity
import org.positive.daymotion.presentation.common.base.viewModelOf
import org.positive.daymotion.presentation.common.bundle
import org.positive.daymotion.presentation.common.extension.startWith
import org.positive.daymotion.presentation.common.model.FeedThumbnailItem
import org.positive.daymotion.presentation.feed.FeedActivity

@AndroidEntryPoint
class CategoryDetailActivity :
    BaseActivity<ActivityCategoryDetailBinding>(R.layout.activity_category_detail) {

    private val viewModel by viewModelOf<CategoryDetailViewModel>()
    private val handler by lazy { Handler() }
    private val feedThumbnailAdapter by lazy { FeedThumbnailAdapter(handler::startFeedActivity) }

    private val title by bundle<String>()
    private val id by bundle<String>()
    private val detailQueryType by bundle<DetailQueryType>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.title = title
        binding.handler = handler
        binding.viewModel = viewModel

        setupViews()
        setupObservers()

        viewModel.loadFeeds(id, detailQueryType)
    }

    private fun setupViews() {
        binding.categoryDetailRecyclerView.apply {
            adapter = feedThumbnailAdapter
            layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        }
    }

    private fun setupObservers() {
        viewModel.categorizedFeedThumbnails.observeNonNull {
            feedThumbnailAdapter.replaceAll(it)
        }
    }

    inner class Handler {
        fun finish() = this@CategoryDetailActivity.finish()

        fun startFeedActivity(item: FeedThumbnailItem) =
            FeedActivity.start(this@CategoryDetailActivity, item.missionName)
    }

    companion object {
        fun start(
            context: Context,
            title: String,
            id: String,
            detailQueryType: DetailQueryType
        ) = context.startWith<CategoryDetailActivity>(
            "title" to title,
            "id" to id,
            "detailQueryType" to detailQueryType
        )
    }
}