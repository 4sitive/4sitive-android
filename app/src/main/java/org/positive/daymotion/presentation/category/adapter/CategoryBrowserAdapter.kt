package org.positive.daymotion.presentation.category.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.positive.daymotion.databinding.ItemCategoryBrowserLargeBinding
import org.positive.daymotion.databinding.ItemCategoryBrowserSmallBinding
import org.positive.daymotion.extension.layoutInflater
import org.positive.daymotion.presentation.category.adapter.holder.CategoryBrowserViewHolder
import org.positive.daymotion.presentation.category.model.CategoryBrowserItem

class CategoryBrowserAdapter : RecyclerView.Adapter<CategoryBrowserViewHolder>() {

    private val items = mutableListOf<CategoryBrowserItem>()

    fun replaceAll(items: List<CategoryBrowserItem>) {
        with(this.items) {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryBrowserViewHolder {
        val layoutInflater = parent.context.layoutInflater
        return when (viewType) {
            0 -> {
                val binding = ItemCategoryBrowserSmallBinding.inflate(layoutInflater, parent, false)
                CategoryBrowserViewHolder.SmallType(binding)
            }
            1 -> {
                val binding = ItemCategoryBrowserLargeBinding.inflate(layoutInflater, parent, false)
                CategoryBrowserViewHolder.LargeType(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(
        holder: CategoryBrowserViewHolder,
        position: Int
    ) {
        val item = items[position]
        when (holder) {
            is CategoryBrowserViewHolder.SmallType -> holder.binding.item = item
            is CategoryBrowserViewHolder.LargeType -> holder.binding.item = item
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = if (items[position].isSmallType) 0 else 1
}