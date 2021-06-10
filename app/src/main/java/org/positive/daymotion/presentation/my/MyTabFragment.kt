package org.positive.daymotion.presentation.my

import android.os.Bundle
import android.view.View
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.FragmentMyTabBinding
import org.positive.daymotion.presentation.base.BaseFragment
import org.positive.daymotion.presentation.base.util.viewModelOf
import org.positive.daymotion.presentation.root.model.RootTabFragment

@AndroidEntryPoint
class MyTabFragment : BaseFragment<FragmentMyTabBinding>(R.layout.fragment_my_tab), RootTabFragment {

    private val viewModel by viewModelOf<MyTabViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        binding.button1.setOnClickListener {
            MyProfileEditActivity.start(requireContext())
        }
    }

    override fun scrollToTop() {
        // TODO(yh): replace to scroll logic
        Toast.makeText(requireContext(), "My", Toast.LENGTH_SHORT).show()
    }
}