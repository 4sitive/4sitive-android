package org.positive.daymotion.presentation.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.FragmentHomeTabBinding
import org.positive.daymotion.presentation.base.BaseFragment
import org.positive.daymotion.presentation.base.util.viewModelOf
import org.positive.daymotion.presentation.feed.FeedActivity
import org.positive.daymotion.presentation.root.model.RootTabFragment
import org.positive.daymotion.presentation.upload.MissionUploadActivity

@AndroidEntryPoint
class HomeTabFragment :
    BaseFragment<FragmentHomeTabBinding>(R.layout.fragment_home_tab), RootTabFragment {

    private val viewModel by viewModelOf<HomeTabViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.button1.setOnClickListener {
            FeedActivity.start(requireContext())
        }
        binding.button2.setOnClickListener {
            MissionUploadActivity.start(requireContext())
        }
    }

    override fun scrollToTop() {
        // TODO(yh): replace to scroll logic
        Toast.makeText(requireContext(), "Home", Toast.LENGTH_SHORT).show()
    }
}