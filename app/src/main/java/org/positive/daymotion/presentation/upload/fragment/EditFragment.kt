package org.positive.daymotion.presentation.upload.fragment

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import org.positive.daymotion.R
import org.positive.daymotion.databinding.FragmentEditBinding
import org.positive.daymotion.presentation.common.base.BaseFragment

class EditFragment : BaseFragment<FragmentEditBinding>(R.layout.fragment_edit) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun updateBackground(@DrawableRes drawableRes: Int?) {
        Glide.with(this)
            .load(drawableRes)
            .into(binding.backgroundImageView)
    }

    fun updateBackground(uri: Uri) {
        Glide.with(this)
            .load(uri)
            .into(binding.backgroundImageView)
    }
}