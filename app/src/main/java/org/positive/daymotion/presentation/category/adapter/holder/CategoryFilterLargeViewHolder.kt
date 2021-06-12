package org.positive.daymotion.presentation.category.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.positive.daymotion.databinding.ItemCategoryFilterLargeBinding
import org.positive.daymotion.databinding.ItemCategoryFilterSmallBinding


sealed class CategoryFilterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    class SmallType(
        val binding: ItemCategoryFilterSmallBinding
    ) : CategoryFilterViewHolder(binding.root)

    class LargeType(
        val binding: ItemCategoryFilterLargeBinding
    ) : CategoryFilterViewHolder(binding.root)
}