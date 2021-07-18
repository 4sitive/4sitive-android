package org.positive.daymotion.presentation.upload.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.positive.daymotion.R
import org.positive.daymotion.databinding.BottomSheetFragmentMissionSelectBinding
import org.positive.daymotion.presentation.common.bundle
import org.positive.daymotion.presentation.upload.model.Mission


class MissionSelectBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private val missions by bundle<Array<Mission>>()
    private val selected by bundle<Mission>()

    private var eventListener: EventListener? = null
    private lateinit var binding: BottomSheetFragmentMissionSelectBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.bottom_sheet_fragment_mission_select,
            container,
            false
        )
        setStyle(DialogFragment.STYLE_NO_FRAME, 0)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BaseBottomSheetDialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.handler = Handler()
        binding.missions = missions.toList()
        binding.selected = selected
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is EventListener) {
            eventListener = context
        }
    }

    inner class Handler {
        fun select(mission: Mission) {
            eventListener?.onMissionSelected(mission)
            dismiss()
        }

        fun cancel() = dismiss()
    }

    interface EventListener {
        fun onMissionSelected(mission: Mission)
    }

    companion object {
        fun newInstance(
            selected: Mission,
            missions: Array<Mission>
        ) = MissionSelectBottomSheetDialogFragment().apply {
            arguments = bundleOf(
                "missions" to missions,
                "selected" to selected
            )
        }
    }
}