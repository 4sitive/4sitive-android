package org.positive.daymotion.common

import android.content.Context
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import org.positive.daymotion.common.LayoutIdMapper.toLayoutId
import org.positive.daymotion.extension.layoutInflater
import java.util.*

class BindingViewHolder<B : ViewDataBinding>(val binding: B) : RecyclerView.ViewHolder(binding.root)

inline fun <reified B : ViewDataBinding> createBindingViewHolder(
    parent: ViewGroup
): BindingViewHolder<B> {
    val context = parent.context
    val layoutInflater = context.layoutInflater
    val layoutId = B::class.java.toLayoutId(context)
    val binding = DataBindingUtil.inflate<B>(layoutInflater, layoutId, parent, false)
    return BindingViewHolder(binding)
}


object LayoutIdMapper {

    private const val replacement = "$1_$2"
    private const val bindingClassSuffix = "Binding"
    private const val resourceType = "layout"

    private val regex = "([a-z])([A-Z]+)".toRegex()

    fun Class<out ViewDataBinding>.toLayoutId(context: Context): Int {
        val layoutIdStr = simpleName
            .removeSuffix(bindingClassSuffix)
            .replace(regex, replacement)
            .toLowerCase(Locale.getDefault())

        return context.resources.getIdentifier(layoutIdStr, resourceType, context.packageName)
    }
}