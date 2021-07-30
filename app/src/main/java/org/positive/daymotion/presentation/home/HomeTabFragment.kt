package org.positive.daymotion.presentation.home

import android.os.Bundle
import android.view.View
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import org.positive.daymotion.R
import org.positive.daymotion.databinding.FragmentHomeTabBinding
import org.positive.daymotion.presentation.common.ScrollableFragment
import org.positive.daymotion.presentation.common.base.BaseFragment
import org.positive.daymotion.presentation.common.base.viewModelOf
import org.positive.daymotion.presentation.home.model.MissionViewItem


@AndroidEntryPoint
class HomeTabFragment : BaseFragment<FragmentHomeTabBinding>(R.layout.fragment_home_tab),
    ScrollableFragment {

    private val viewModel by viewModelOf<HomeTabViewModel>()

    private val missions = listOf(
        "아아? 라떼? 커피를 추천해줘!",
        "털복숭이 친구들을 소개해줘요!",
        "도시의 밤 향기를 닮은 너!"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.missionItems = makeMissionViewItem(missions)
        binding.handler = Handler()
        binding.viewModel = viewModel
    }

    private fun makeMissionViewItem(missions: List<String>): List<MissionViewItem> {
        val randomBackgroundList = mutableListOf(
            R.drawable.bananamania to R.drawable.bananamania_art,
            R.drawable.can to R.drawable.can_art,
            R.drawable.chetwodeblue to R.drawable.chetwodeblue_art,
            R.drawable.darksalmon to R.drawable.darksalmon_art,
            R.drawable.flare to R.drawable.flare_art,
            R.drawable.flax to R.drawable.flax_art,
            R.drawable.fuchsia to R.drawable.fuchsia_art,
            R.drawable.mediumpurple to R.drawable.mediumpurple_art,
            R.drawable.mindaro to R.drawable.mindaro_art,
            R.drawable.palevioletred to R.drawable.palevioletred_art,
            R.drawable.ronchi to R.drawable.ronchi_art,
            R.drawable.witch_haze to R.drawable.witch_haze_art,
        ).apply { shuffle() }

        return missions.mapIndexed { i, mission ->
            MissionViewItem(
                mission,
                randomBackgroundList[i].first,
                randomBackgroundList[i].second
            )
        }
    }

    override fun scrollToTop() {
        binding.scrollView.smoothScrollTo(0, binding.profileLayout.top)
    }

    inner class Handler {
        fun sendSubject(missionViewItem: MissionViewItem) =
            PostListActivity.start(requireContext(), missionViewItem)
    }
}
