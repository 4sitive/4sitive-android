package org.positive.daymotion.presentation.category.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.positive.daymotion.databinding.ItemCategoryDetailBinding
import org.positive.daymotion.extension.layoutInflater
import org.positive.daymotion.presentation.category.adapter.holder.CategoryDetailViewHolder
import org.positive.daymotion.presentation.category.model.CategoryDetailItem

class CategoryDetailAdapter : RecyclerView.Adapter<CategoryDetailViewHolder>() {

    private val items = mutableListOf<CategoryDetailItem>()

    fun replaceAll(items: List<CategoryDetailItem>) {
        with(this.items) {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryDetailViewHolder {
        val layoutInflater = parent.context.layoutInflater
        val binding = ItemCategoryDetailBinding.inflate(layoutInflater, parent, false)
        return CategoryDetailViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CategoryDetailViewHolder,
        position: Int
    ) {
        val item = items[position]
        holder.binding.item = item
    }

    override fun getItemCount() = items.size
}