package org.positive.daymotion.presentation.common.bindingadapter

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
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

@BindingAdapter("src")
fun ImageView.setImageView(@DrawableRes resourceId: Int?) {
    Glide.with(this)
        .load(resourceId)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

@BindingAdapter("srcWithCircle")
fun ImageView.setImageWithCircleCropBindingAdapter(@DrawableRes resourceId: Int?) {
    Glide.with(this)
        .load(resourceId)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

@BindingAdapter("srcWithCircle")
fun ImageView.setImageWithCircleCropBindingAdapter(drawable: Drawable?) {
    Glide.with(this)
        .load(drawable)
        .circleCrop()
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

@BindingAdapter("srcWithCircle")
fun ImageView.setImageWithCircleCropBindingAdapter(uri: String?) {
    Glide.with(this)
        .load(uri)
        .circleCrop()
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

@BindingAdapter("srcWithCircle")
fun ImageView.setImageWithCircleCropBindingAdapter(uri: Uri?) {
    Glide.with(this)
        .load(uri)
        .circleCrop()
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

@BindingAdapter("srcWithCircle", "defaultImage", requireAll = true)
fun ImageView.setImageWithCircleCropBindingAdapter(uri: String?, defaultImage: Drawable?) {
    Glide.with(this)
        .load(uri.let { if (!it.isNullOrBlank()) it else defaultImage })
        .circleCrop()
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}