package org.positive.daymotion.common.bindingadapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("srcWithCircle")
fun ImageView.setImageWithCircleCropBindingAdapter(drawable: Drawable?) {
    Glide.with(this)
        .load(drawable)
        .circleCrop()
        .into(this)
}

@BindingAdapter("srcWithCircle")
fun ImageView.setImageWithCircleCropBindingAdapter(uri: String?) {
    Glide.with(this)
        .load(uri)
        .circleCrop()
        .into(this)
}

@BindingAdapter("srcWithCircle", "defaultImage", requireAll = true)
fun ImageView.setImageWithCircleCropBindingAdapter(uri: String?, defaultImage: Drawable?) {
    Glide.with(this)
        .load(uri ?: defaultImage)
        .circleCrop()
        .into(this)
}