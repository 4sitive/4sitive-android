package org.positive.daymotion.presentation.common.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.positive.daymotion.presentation.common.BindingViewHolder
import org.positive.daymotion.presentation.common.createBindingViewHolder
import org.positive.daymotion.databinding.ItemFeedThumbnailBinding
import org.positive.daymotion.presentation.common.model.FeedThumbnailItem

class FeedThumbnailAdapter : RecyclerView.Adapter<BindingViewHolder<ItemFeedThumbnailBinding>>() {

    private val items = mutableListOf<FeedThumbnailItem>()

    fun replaceAll(items: List<FeedThumbnailItem>) {
        with(this.items) {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<ItemFeedThumbnailBinding> = createBindingViewHolder(parent)

    override fun onBindViewHolder(
        holder: BindingViewHolder<ItemFeedThumbnailBinding>,
        position: Int
    ) {
        holder.binding.item = items[position]
    }

    override fun getItemCount() = items.size
}