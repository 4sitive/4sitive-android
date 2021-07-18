package org.positive.daymotion.presentation.upload.fragment

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import org.positive.daymotion.R
import org.positive.daymotion.databinding.FragmentEditBinding
import org.positive.daymotion.presentation.common.base.BaseFragment

class EditFragment : BaseFragment<FragmentEditBinding>(R.layout.fragment_edit) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun updateBackground(@DrawableRes drawableRes: Int?) {
        binding.backgroundImageView.setImageResource(drawableRes ?: return)
    }

    fun updateBackground(uri: Uri) {
        binding.backgroundImageView.setImageURI(uri)
    }
}