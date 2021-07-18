package org.positive.daymotion.presentation.upload.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import org.positive.daymotion.BR
import org.positive.daymotion.R
import org.positive.daymotion.databinding.ItemCameraButtonBinding
import org.positive.daymotion.databinding.ItemDefaultBackgroundButtonBinding
import org.positive.daymotion.presentation.common.BindingViewHolder
import org.positive.daymotion.presentation.common.createBindingViewHolder
import org.positive.daymotion.presentation.upload.model.BackgroundSelection

class BackgroundSelectionAdapter(context: Context) :
    RecyclerView.Adapter<BindingViewHolder<out ViewDataBinding>>() {

    private val selections = initSelections(context)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<out ViewDataBinding> = when (viewType) {
        CAMERA_BUTTON_TYPE ->
            createBindingViewHolder<ItemCameraButtonBinding>(parent)
        DEFAULT_BACKGROUND_TYPE ->
            createBindingViewHolder<ItemDefaultBackgroundButtonBinding>(parent)
        else -> throw IllegalStateException("Invalid view type")
    }

    override fun onBindViewHolder(
        holder: BindingViewHolder<out ViewDataBinding>,
        position: Int
    ) {
        holder.binding.setVariable(BR.item, selections[position])
    }

    override fun getItemCount() = selections.size

    override fun getItemViewType(position: Int) = when (selections[position]) {
        is BackgroundSelection.Camera -> CAMERA_BUTTON_TYPE
        is BackgroundSelection.Default -> DEFAULT_BACKGROUND_TYPE
    }

    private fun initSelections(context: Context): List<BackgroundSelection> {
        val default = requireNotNull(ContextCompat.getDrawable(context, R.color._171717))
        val defaultSelections = listOf(
            R.drawable.img_feed_thumb_01,
            R.drawable.img_feed_thumb_02,
            R.drawable.img_feed_thumb_03,
            R.drawable.img_feed_thumb_04,
            R.drawable.img_feed_thumb_05,
            R.drawable.img_feed_thumb_06,
            R.drawable.img_feed_thumb_07,
            R.drawable.img_feed_thumb_08,
        ).map {
            val drawable = ContextCompat.getDrawable(context, it) ?: default
            BackgroundSelection.Default(drawable)
        }
        return mutableListOf(BackgroundSelection.Camera()) + defaultSelections
    }

    companion object {
        private const val CAMERA_BUTTON_TYPE = 0
        private const val DEFAULT_BACKGROUND_TYPE = 1
    }
}