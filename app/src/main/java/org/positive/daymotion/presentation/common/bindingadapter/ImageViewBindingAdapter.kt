package org.positive.daymotion.presentation.common.bindingadapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@BindingAdapter("src")
fun ImageView.setImageView(model: Any?) {
    Glide.with(this)
        .load(model)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

@BindingAdapter("srcWithCircle")
fun ImageView.setImageWithCircleCropBindingAdapter(model: Any?) {
    Glide.with(this)
        .load(model)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

@BindingAdapter("srcWithCircle", "defaultImage", requireAll = true)
fun ImageView.setImageWithCircleCropBindingAdapter(model: Any?, defaultImage: Drawable?) {
    Glide.with(this)
        .load(model)
        .placeholder(defaultImage)
        .circleCrop()
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}