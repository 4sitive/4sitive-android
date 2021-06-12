package org.positive.daymotion.presentation.category.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.positive.daymotion.databinding.ItemCategoryFilterLargeBinding
import org.positive.daymotion.databinding.ItemCategoryFilterSmallBinding
import org.positive.daymotion.extension.layoutInflater
import org.positive.daymotion.presentation.category.adapter.holder.CategoryFilterViewHolder
import org.positive.daymotion.presentation.category.model.CategoryFilterItem

class CategoryFilterAdapter : RecyclerView.Adapter<CategoryFilterViewHolder>() {

    private val items = mutableListOf<CategoryFilterItem>()

    fun replaceAll(items: List<CategoryFilterItem>) {
        with(this.items) {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryFilterViewHolder {
        val layoutInflater = parent.context.layoutInflater
        return when (viewType) {
            0 -> {
                val binding = ItemCategoryFilterSmallBinding.inflate(layoutInflater, parent, false)
                CategoryFilterViewHolder.SmallType(binding)
            }
            1 -> {
                val binding = ItemCategoryFilterLargeBinding.inflate(layoutInflater, parent, false)
                CategoryFilterViewHolder.LargeType(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(
        holder: CategoryFilterViewHolder,
        position: Int
    ) {
        val item = items[position]
        when (holder) {
            is CategoryFilterViewHolder.SmallType -> holder.binding.item = item
            is CategoryFilterViewHolder.LargeType -> holder.binding.item = item
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = if (items[position].isSmallType) 0 else 1
}