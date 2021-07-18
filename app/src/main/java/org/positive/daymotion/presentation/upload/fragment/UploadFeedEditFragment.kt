package org.positive.daymotion.presentation.upload.fragment

import android.os.Bundle
import android.view.View
import org.positive.daymotion.R
import org.positive.daymotion.databinding.FragmentUploadFeedEditBinding
import org.positive.daymotion.presentation.common.base.BaseFragment
import org.positive.daymotion.presentation.common.base.viewModelOf
import org.positive.daymotion.presentation.upload.model.Mission
import org.positive.daymotion.presentation.upload.viewmodel.UploadFeedEditViewModel

class UploadFeedEditFragment :
    BaseFragment<FragmentUploadFeedEditBinding>(R.layout.fragment_upload_feed_edit) {

    private val viewModel by viewModelOf<UploadFeedEditViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

    fun updateBackground(background: Any?) {
        viewModel.updateBackground(background)
    }

    fun updateSelectedMission(mission: Mission) {
        viewModel.updateSelectedMission(mission)
    }
}