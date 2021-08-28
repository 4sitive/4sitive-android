package org.positive.daymotion.presentation.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.positive.daymotion.databinding.ItemTodayMissionBinding
import org.positive.daymotion.presentation.common.BindingViewHolder
import org.positive.daymotion.presentation.common.createBindingViewHolder
import org.positive.daymotion.presentation.home.fragment.HomeTabFragment
import org.positive.daymotion.presentation.home.model.MissionViewItem

class TodayMissionAdapter constructor(
    private val handler: HomeTabFragment.Handler
) : RecyclerView.Adapter<BindingViewHolder<ItemTodayMissionBinding>>() {

    private val items = mutableListOf<MissionViewItem>()

    fun replaceAll(items: List<MissionViewItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = createBindingViewHolder<ItemTodayMissionBinding>(parent)

    override fun onBindViewHolder(
        holder: BindingViewHolder<ItemTodayMissionBinding>,
        position: Int
    ) {
        holder.binding.item = items[position]
        holder.binding.handler = handler
    }

    override fun getItemCount() = items.size
}