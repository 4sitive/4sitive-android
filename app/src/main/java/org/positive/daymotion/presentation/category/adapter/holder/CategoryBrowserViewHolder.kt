package org.positive.daymotion.presentation.category.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.positive.daymotion.databinding.ItemCategoryBrowserLargeBinding
import org.positive.daymotion.databinding.ItemCategoryBrowserSmallBinding


sealed class CategoryBrowserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    class SmallType(
        val binding: ItemCategoryBrowserSmallBinding
    ) : CategoryBrowserViewHolder(binding.root)

    class LargeType(
        val binding: ItemCategoryBrowserLargeBinding
    ) : CategoryBrowserViewHolder(binding.root)
}