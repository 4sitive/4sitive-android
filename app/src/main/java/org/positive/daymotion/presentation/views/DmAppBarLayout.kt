package org.positive.daymotion.presentation.views

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.appbar.AppBarLayout
import org.positive.daymotion.databinding.WidgetDmAppBarLayoutBinding
import org.positive.daymotion.extension.layoutInflater

class DmAppBarLayout : AppBarLayout {

    private val binding: WidgetDmAppBarLayoutBinding =
        WidgetDmAppBarLayoutBinding.inflate(context.layoutInflater, this, true)

    constructor(
        context: Context
    ) : super(context)

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    fun setTitle(title: String) {
        binding.title.text = title
    }

    fun setOnBackButtonClick(onClickListener: OnClickListener) {
        binding.backButton.setOnClickListener(onClickListener)
    }
}