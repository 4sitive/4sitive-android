package org.positive.daymotion.presentation.upload.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import org.positive.daymotion.BR
import org.positive.daymotion.databinding.ItemCameraButtonBinding
import org.positive.daymotion.databinding.ItemCutomImageButtonBinding
import org.positive.daymotion.databinding.ItemDefaultBackgroundButtonBinding
import org.positive.daymotion.presentation.common.BindingViewHolder
import org.positive.daymotion.presentation.common.createBindingViewHolder
import org.positive.daymotion.presentation.upload.activity.FeedUploadActivity
import org.positive.daymotion.presentation.upload.model.BackgroundSelection
import kotlin.properties.Delegates

class BackgroundSelectionAdapter(
    private val handler: FeedUploadActivity.Handler
) : RecyclerView.Adapter<BindingViewHolder<out ViewDataBinding>>() {

    var selections by Delegates.observable<List<BackgroundSelection>>(emptyList()) { _, old, _ ->
        val isFirst = old.isEmpty()
        if (isFirst) {
            notifyDataSetChanged()
        } else {
            notifyItemChanged(0)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<out ViewDataBinding> = when (viewType) {
        CAMERA_BUTTON_TYPE ->
            createBindingViewHolder<ItemCameraButtonBinding>(parent)
        CUSTOM_IMAGE_TYPE ->
            createBindingViewHolder<ItemCutomImageButtonBinding>(parent)
        DEFAULT_BACKGROUND_TYPE ->
            createBindingViewHolder<ItemDefaultBackgroundButtonBinding>(parent)
        else -> throw IllegalStateException("Invalid view type")
    }

    override fun onBindViewHolder(
        holder: BindingViewHolder<out ViewDataBinding>,
        position: Int
    ) {
        with(holder.binding) {
            setVariable(BR.handler, handler)
            setVariable(BR.item, selections[position])
        }
    }

    override fun getItemCount() = selections.size

    override fun getItemViewType(position: Int) = when (selections[position]) {
        is BackgroundSelection.Camera -> CAMERA_BUTTON_TYPE
        is BackgroundSelection.Custom -> CUSTOM_IMAGE_TYPE
        is BackgroundSelection.Default -> DEFAULT_BACKGROUND_TYPE
    }

    companion object {
        private const val CAMERA_BUTTON_TYPE = 0
        private const val CUSTOM_IMAGE_TYPE = 1
        private const val DEFAULT_BACKGROUND_TYPE = 2
    }
}