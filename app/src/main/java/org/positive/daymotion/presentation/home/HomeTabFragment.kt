package org.positive.daymotion.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
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

        val (randomColors, randomEffects) = selectBackground()
        val cardArray = arrayListOf(binding.homeMissionCard1, binding.homeMissionCard2, binding.homeMissionCard3)
        val effectArray = arrayListOf(binding.CardEffectImageView1, binding.CardEffectImageView2, binding.CardEffectImageView3)
        val textViewArray = arrayListOf(binding.missionTextView1, binding.missionTextView2, binding.missionTextView3)

        for ((i, card) in cardArray.withIndex()) {
            textViewArray[i].text = missionArray[i]
            effectArray[i].setImageResource(randomEffects[i] as Int)
            card.background = ContextCompat.getDrawable(requireContext(), randomColors[i] as Int)
            card.setOnClickListener {
                sendSubject(missionArray[i], randomColors[i] as Int, randomEffects[i] as Int)
            }
        }
    }

    private fun sendSubject(mission: String, color: Int, effect: Int) {
        val intent = Intent(requireContext(), PostListActivity::class.java)
        intent.putExtra("mission", mission)
        intent.putExtra("color", color)
        intent.putExtra("effect", effect)
        startActivity(intent)
    }

    private fun selectBackground(): Pair<ArrayList<Int?>, ArrayList<Int?>> {
        val backgroundColorArray = arrayListOf(
            mapOf("color" to R.drawable.bananamania, "effect" to R.drawable.bananamania_art),
            mapOf("color" to R.drawable.can, "effect" to R.drawable.can_art),
            mapOf("color" to R.drawable.chetwodeblue, "effect" to R.drawable.chetwodeblue_art),
            mapOf("color" to R.drawable.darksalmon, "effect" to R.drawable.darksalmon_art),
            mapOf("color" to R.drawable.flare, "effect" to R.drawable.flare_art),
            mapOf("color" to R.drawable.flax, "effect" to R.drawable.flax_art),
            mapOf("color" to R.drawable.fuchsia, "effect" to R.drawable.fuchsia_art),
            mapOf("color" to R.drawable.mediumpurple, "effect" to R.drawable.mediumpurple_art),
            mapOf("color" to R.drawable.mindaro, "effect" to R.drawable.mindaro_art),
            mapOf("color" to R.drawable.palevioletred, "effect" to R.drawable.palevioletred_art),
            mapOf("color" to R.drawable.ronchi, "effect" to R.drawable.ronchi_art),
            mapOf("color" to R.drawable.witch_haze, "effect" to R.drawable.witch_haze_art),
        )
        val randomArray = backgroundColorArray.shuffled()
        val colors = arrayListOf(randomArray[0]["color"], randomArray[1]["color"], randomArray[2]["color"])
        val effects = arrayListOf(randomArray[0]["effect"], randomArray[1]["effect"], randomArray[2]["effect"])

        return Pair(colors, effects)
    }

    override fun scrollToTop() {
        binding.scrollView.smoothScrollTo(0, binding.profileLayout.top)
    }
}
