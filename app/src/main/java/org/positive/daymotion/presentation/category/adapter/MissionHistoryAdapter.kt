package org.positive.daymotion.presentation.category.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.positive.daymotion.common.BindingViewHolder
import org.positive.daymotion.common.createBindingViewHolder
import org.positive.daymotion.databinding.ItemMissionHistoryBinding
import org.positive.daymotion.presentation.category.fragment.MissionHistoryPageFragment
import org.positive.daymotion.presentation.category.model.MissionHistoryItem

class MissionHistoryAdapter(
    private val handler: MissionHistoryPageFragment.Handler
) : RecyclerView.Adapter<BindingViewHolder<ItemMissionHistoryBinding>>() {

    private val items = mutableListOf<MissionHistoryItem>()

    fun replaceAll(items: List<MissionHistoryItem>) {
        with(this.items) {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<ItemMissionHistoryBinding> = createBindingViewHolder(parent)

    override fun onBindViewHolder(
        holder: BindingViewHolder<ItemMissionHistoryBinding>,
        position: Int
    ) {
        with(holder.binding) {
            item = items[position]
            handler = this@MissionHistoryAdapter.handler
        }
    }

    override fun getItemCount() = items.size
}