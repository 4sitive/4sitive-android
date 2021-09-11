package org.positive.daymotion.presentation.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.positive.daymotion.databinding.ItemEmojiBinding
import org.positive.daymotion.presentation.common.BindingViewHolder
import org.positive.daymotion.presentation.common.createBindingViewHolder
import org.positive.daymotion.presentation.home.model.EmojiItem

class EmojiItemAdapter : RecyclerView.Adapter<BindingViewHolder<ItemEmojiBinding>>() {

    private val items = mutableListOf<EmojiItem>()

    fun replaceAll(items: List<EmojiItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun getItems() :List<EmojiItem> = items

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<ItemEmojiBinding> = createBindingViewHolder(parent)

    override fun onBindViewHolder(
        holder: BindingViewHolder<ItemEmojiBinding>,
        position: Int
    ) {
        holder.binding.item = items[position]
    }

    override fun getItemCount() = items.size
}