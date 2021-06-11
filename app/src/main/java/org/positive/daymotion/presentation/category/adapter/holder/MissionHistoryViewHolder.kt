package org.positive.daymotion.presentation.category.adapter.holder

import androidx.recyclerview.widget.RecyclerView
import org.positive.daymotion.databinding.ItemMissionHistoryBinding
import org.positive.daymotion.presentation.category.adapter.MissionHistoryInnerAdapter
import org.positive.daymotion.presentation.category.model.MissionHistoryItem

class MissionHistoryViewHolder(
    private val binding: ItemMissionHistoryBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val adapter = MissionHistoryInnerAdapter()

    init {
        binding.missionsRecyclerView.adapter = adapter
    }

    fun bind(missionHistoryItem: MissionHistoryItem) {
        binding.item = missionHistoryItem
        adapter.replaceAll(missionHistoryItem.missions)
    }
}