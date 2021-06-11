package org.positive.daymotion.presentation.category.adapter.holder

import androidx.core.view.children
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.positive.daymotion.databinding.ItemMissionHistoryBinding
import org.positive.daymotion.presentation.category.adapter.MissionHistoryInnerAdapter
import org.positive.daymotion.presentation.category.model.MissionHistoryItem

class MissionHistoryViewHolder(
    private val binding: ItemMissionHistoryBinding,
    recycledViewPool: RecyclerView.RecycledViewPool
) : RecyclerView.ViewHolder(binding.root) {

    val innerScrollPosition
        get() = linearLayoutManager.findFirstVisibleItemPosition()

    val innerScrollPositionOffset
        get() = binding.missionsRecyclerView.computeHorizontalScrollOffset()

    private val missionHistoryInnerAdapter = MissionHistoryInnerAdapter()

    private val linearLayoutManager =
        LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL, false)

    init {
        binding.missionsRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = missionHistoryInnerAdapter
            setRecycledViewPool(recycledViewPool)
        }
    }

    fun bind(item: MissionHistoryItem) {
        binding.item = item
        missionHistoryInnerAdapter.replaceAll(item.missions)
        restoreScroll(item)
    }

    private fun restoreScroll(item: MissionHistoryItem) {
        if (item.savedPosition == 0 && item.savedPositionOffset == 0) {
            return
        }

        val childView = binding.missionsRecyclerView.children.first()
        val width = childView.width + childView.marginStart + childView.marginEnd
        val offset = item.savedPositionOffset - item.savedPosition * width

        linearLayoutManager.scrollToPositionWithOffset(item.savedPosition, -offset)
    }
}