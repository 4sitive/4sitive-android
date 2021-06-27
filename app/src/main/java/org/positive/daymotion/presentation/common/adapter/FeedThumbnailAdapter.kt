package org.positive.daymotion.presentation.common.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.positive.daymotion.databinding.ItemFeedThumbnailBinding
import org.positive.daymotion.presentation.common.BindingViewHolder
import org.positive.daymotion.presentation.common.createBindingViewHolder
import org.positive.daymotion.presentation.common.model.FeedThumbnailItem

class FeedThumbnailAdapter(
    private val onItemClicked: (FeedThumbnailItem) -> Unit = {}
) : RecyclerView.Adapter<BindingViewHolder<ItemFeedThumbnailBinding>>() {

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
    ): BindingViewHolder<ItemFeedThumbnailBinding> =
        createBindingViewHolder<ItemFeedThumbnailBinding>(parent).apply {
            binding.itemContainer.setOnClickListener {
                items.getOrNull(bindingAdapterPosition)?.let(onItemClicked)
            }
        }

    override fun onBindViewHolder(
        holder: BindingViewHolder<ItemFeedThumbnailBinding>,
        position: Int
    ) {
        with(holder.binding) {
            item = items[position]
        }
    }

    override fun getItemCount() = items.size
}