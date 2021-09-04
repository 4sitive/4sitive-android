package org.positive.daymotion.presentation.upload.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.positive.daymotion.databinding.ItemMissionSelectionBinding
import org.positive.daymotion.presentation.common.BindingViewHolder
import org.positive.daymotion.presentation.common.createBindingViewHolder
import org.positive.daymotion.presentation.upload.fragment.MissionSelectBottomSheetDialogFragment
import org.positive.daymotion.presentation.upload.model.MissionViewItem


class MissionSelectionAdapter(
    private val handler: MissionSelectBottomSheetDialogFragment.Handler,
    private val selected: MissionViewItem
) :
    RecyclerView.Adapter<BindingViewHolder<ItemMissionSelectionBinding>>() {

    private val items = mutableListOf<MissionViewItem>()

    fun replaceAll(items: List<MissionViewItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = createBindingViewHolder<ItemMissionSelectionBinding>(parent)

    override fun onBindViewHolder(
        holder: BindingViewHolder<ItemMissionSelectionBinding>,
        position: Int
    ) {
        holder.binding.handler = handler
        holder.binding.item = items[position]
        holder.binding.selected = items[position] == selected
    }

    override fun getItemCount() = items.size
}