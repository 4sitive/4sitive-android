package org.positive.daymotion.presentation.category.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.positive.daymotion.common.BindingViewHolder
import org.positive.daymotion.common.createBindingViewHolder
import org.positive.daymotion.databinding.ItemCategoryDetailBinding
import org.positive.daymotion.presentation.category.model.CategoryDetailItem

class CategoryDetailAdapter : RecyclerView.Adapter<BindingViewHolder<ItemCategoryDetailBinding>>() {

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
    ): BindingViewHolder<ItemCategoryDetailBinding> = createBindingViewHolder(parent)

    override fun onBindViewHolder(
        holder: BindingViewHolder<ItemCategoryDetailBinding>,
        position: Int
    ) {
        holder.binding.item = items[position]
    }

    override fun getItemCount() = items.size
}