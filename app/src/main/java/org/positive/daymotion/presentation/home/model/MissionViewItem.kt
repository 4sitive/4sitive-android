package org.positive.daymotion.presentation.home.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import org.positive.daymotion.R
import org.positive.daymotion.domain.Mission

@Parcelize
data class MissionViewItem(
    val categoryName: String,
    val content: String,
    val date: String,
    val id: String,
    val image: String,
    val question: String,
    @DrawableRes val background: Int,
    @DrawableRes val effect: Int,
) : Parcelable {

    companion object {
        private val backgroundSet: List<Pair<Int, Int>> by lazy {
            mutableListOf(
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
        }

        fun of(mission: Mission, index: Int) = MissionViewItem(
            mission.categoryName,
            mission.content,
            mission.date,
            mission.id,
            mission.image,
            mission.question,
            backgroundSet[index].first,
            backgroundSet[index].second
        )
    }
}