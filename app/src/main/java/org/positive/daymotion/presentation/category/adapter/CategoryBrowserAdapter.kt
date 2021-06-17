package org.positive.daymotion.presentation.category.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.positive.daymotion.databinding.ItemCategoryBrowserBinding
import org.positive.daymotion.extension.layoutInflater
import org.positive.daymotion.presentation.category.adapter.holder.CategoryBrowserViewHolder
import org.positive.daymotion.presentation.category.fragment.CategoryBrowserPageFragment
import org.positive.daymotion.presentation.category.model.CategoryBrowserItem

class CategoryBrowserAdapter(
    private val handler: CategoryBrowserPageFragment.Handler
) : RecyclerView.Adapter<CategoryBrowserViewHolder>() {

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
        val binding = ItemCategoryBrowserBinding.inflate(layoutInflater, parent, false)
        binding.itemContainer.setOnClickListener { handler.goToCategoryDetail() }
        return CategoryBrowserViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CategoryBrowserViewHolder,
        position: Int
    ) {
        val item = items[position]
        holder.binding.item = item
    }

    override fun getItemCount() = items.size
}