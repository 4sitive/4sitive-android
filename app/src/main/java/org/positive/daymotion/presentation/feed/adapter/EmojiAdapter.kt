package org.positive.daymotion.presentation.feed.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import org.positive.daymotion.BR
import org.positive.daymotion.databinding.ItemAddEmojiBinding
import org.positive.daymotion.databinding.ItemEmojiBinding
import org.positive.daymotion.presentation.common.BindingViewHolder
import org.positive.daymotion.presentation.common.createBindingViewHolder
import org.positive.daymotion.presentation.feed.model.EmojiItem

class EmojiAdapter : RecyclerView.Adapter<BindingViewHolder<out ViewDataBinding>>() {

    private val items = mutableListOf<EmojiItem>()

    fun replaceAll(items: List<EmojiItem>) {
        with(this.items) {
            clear()
            addAll(items + EmojiItem.AddEmoji)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<out ViewDataBinding> = when (viewType) {
        EMOJI_VIEW_TYPE -> createBindingViewHolder<ItemEmojiBinding>(parent)
        EMOJI_ADD_VIEW_TYPE -> createBindingViewHolder<ItemAddEmojiBinding>(parent)
        else -> throw IllegalStateException("Invalid view type")
    }

    override fun onBindViewHolder(holder: BindingViewHolder<out ViewDataBinding>, position: Int) {
        holder.binding.setVariable(BR.item, items[position])
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = when (items[position]) {
        is EmojiItem.Emoji -> EMOJI_VIEW_TYPE
        is EmojiItem.AddEmoji -> EMOJI_ADD_VIEW_TYPE
    }

    companion object {
        private const val EMOJI_VIEW_TYPE = 0
        private const val EMOJI_ADD_VIEW_TYPE = 1
    }
}