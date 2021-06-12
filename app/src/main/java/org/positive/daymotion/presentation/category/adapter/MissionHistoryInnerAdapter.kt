package org.positive.daymotion.presentation.category.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.positive.daymotion.databinding.ItemMissionHistoryInnerBinding
import org.positive.daymotion.extension.layoutInflater
import org.positive.daymotion.presentation.category.adapter.holder.MissionHistoryInnerViewHolder
import org.positive.daymotion.presentation.category.model.MissionHistoryInnerItem

class MissionHistoryInnerAdapter : RecyclerView.Adapter<MissionHistoryInnerViewHolder>() {

    private val items = mutableListOf<MissionHistoryInnerItem>()

    fun replaceAll(items: List<MissionHistoryInnerItem>) {
        with(this.items) {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MissionHistoryInnerViewHolder {
        val layoutInflater = parent.context.layoutInflater
        val binding = ItemMissionHistoryInnerBinding.inflate(layoutInflater, parent, false)
        return MissionHistoryInnerViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MissionHistoryInnerViewHolder,
        position: Int
    ) {
        holder.binding.item = items[position]
    }

    override fun getItemCount() = items.size
}