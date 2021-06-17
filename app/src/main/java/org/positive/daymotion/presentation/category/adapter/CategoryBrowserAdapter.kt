package org.positive.daymotion.presentation.category.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.positive.daymotion.common.BindingViewHolder
import org.positive.daymotion.common.createBindingViewHolder
import org.positive.daymotion.databinding.ItemCategoryBrowserBinding
import org.positive.daymotion.presentation.category.fragment.CategoryBrowserPageFragment
import org.positive.daymotion.presentation.category.model.CategoryBrowserItem

class CategoryBrowserAdapter(
    private val handler: CategoryBrowserPageFragment.Handler
) : RecyclerView.Adapter<BindingViewHolder<ItemCategoryBrowserBinding>>() {

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
    ): BindingViewHolder<ItemCategoryBrowserBinding> = createBindingViewHolder(parent)

    override fun onBindViewHolder(
        holder: BindingViewHolder<ItemCategoryBrowserBinding>,
        position: Int
    ) {
        with(holder.binding) {
            item = items[position]
            handler = this@CategoryBrowserAdapter.handler
        }
    }

    override fun getItemCount() = items.size
}