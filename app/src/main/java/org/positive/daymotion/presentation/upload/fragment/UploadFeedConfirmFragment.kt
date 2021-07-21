package org.positive.daymotion.presentation.upload.fragment

import android.os.Bundle
import android.view.View
import org.positive.daymotion.R
import org.positive.daymotion.databinding.FragmentUploadFeedConfirmBinding
import org.positive.daymotion.presentation.common.base.BaseFragment
import org.positive.daymotion.presentation.common.base.sharedViewModelOf
import org.positive.daymotion.presentation.common.base.viewModelOf
import org.positive.daymotion.presentation.upload.activity.UploadFeedTextEditActivity
import org.positive.daymotion.presentation.upload.model.Mission
import org.positive.daymotion.presentation.upload.viewmodel.FeedUploadViewModel
import org.positive.daymotion.presentation.upload.viewmodel.UploadFeedConfirmViewModel

class UploadFeedConfirmFragment :
    BaseFragment<FragmentUploadFeedConfirmBinding>(R.layout.fragment_upload_feed_confirm) {

    private val handler = Handler()
    private val viewModel by viewModelOf<UploadFeedConfirmViewModel>()
    private val sharedViewModel by sharedViewModelOf<FeedUploadViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.handler = handler
        binding.viewModel = viewModel
        binding.sharedViewModel = sharedViewModel
    }

    fun updateBackground(background: Any?) {
        viewModel.updateBackground(background)
    }

    fun updateSelectedMission(mission: Mission) {
        viewModel.updateSelectedMission(mission)
    }

    inner class Handler {
        fun startUploadFeedTextEditActivity() = UploadFeedTextEditActivity.start(requireContext())
    }
}