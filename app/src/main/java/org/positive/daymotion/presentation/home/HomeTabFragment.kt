package org.positive.daymotion.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.FragmentHomeTabBinding
import org.positive.daymotion.presentation.common.ScrollableFragment
import org.positive.daymotion.presentation.common.base.BaseFragment
import org.positive.daymotion.presentation.common.base.viewModelOf

@AndroidEntryPoint
class HomeTabFragment : BaseFragment<FragmentHomeTabBinding>(R.layout.fragment_home_tab), ScrollableFragment {

    private val viewModel by viewModelOf<HomeTabViewModel>()
    private val missionArray = arrayListOf("아아? 라떼? 커피를 추천해줘!", "털복숭이 친구들을 소개해줘요!", "도시의 밤 향기를 닮은 너!")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        val cardArray = arrayListOf(binding.homeMissionCard1, binding.homeMissionCard2, binding.homeMissionCard3)
        val textViewArray = arrayListOf(binding.missionTextView1, binding.missionTextView2, binding.missionTextView3)
        for ((i, card) in cardArray.withIndex()) {
            textViewArray[i].text = missionArray[i]
            card.setOnClickListener {
                sendSubject(missionArray[i])
            }
        }
    }

    private fun sendSubject(text: String) {
        val intent = Intent(requireContext(), HomeDetailActivity::class.java)
        intent.putExtra("subject", text)
        startActivity(intent)
    }

    override fun scrollToTop() {
        binding.scrollView.smoothScrollTo(0, binding.profileLayout.top)
    }
}
