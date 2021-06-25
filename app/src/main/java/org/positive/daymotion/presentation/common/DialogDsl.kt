package org.positive.daymotion.presentation.common

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import org.positive.daymotion.R
import org.positive.daymotion.databinding.WidgetCommonDialogBinding
import java.io.Serializable


class DialogScope : Serializable {

    var title: String = ""

    var content: String = ""

    var blueButtonText: String = "확인"

    var grayButtonText: String = "취소"

    var isVisibleGrayButton: Boolean = false

    var isCancelable: Boolean = false

    private var onBlueButton: () -> Unit = {}

    private var onGrayButton: () -> Unit = {}

    fun onClickBlueButton(block: () -> Unit) {
        onBlueButton = block
    }

    fun onClickGrayButton(block: () -> Unit) {
        onGrayButton = block
    }

    fun build(context: Activity): Dialog {
        val binding = WidgetCommonDialogBinding.inflate(context.layoutInflater, null, false)

        val dialog = AlertDialog.Builder(context, R.style.CommonDialogTheme)
            .setView(binding.root)
            .create()

        with(binding) {
            titleTextView.text = title
            contentTextView.text = content
            blueButton.text = blueButtonText
            grayButton.text = grayButtonText
            if (!isVisibleGrayButton) {
                grayButton.visibility = View.GONE
            }
            blueButton.setOnClickListener {
                blueButton.alpha = 0.5f
                onBlueButton()
                dialog.dismiss()
            }
            grayButton.setOnClickListener {
                grayButton.alpha = 0.5f
                onGrayButton()
                dialog.dismiss()
            }
        }

        return dialog
    }
}

class CommonDialogFragment : DialogFragment() {

    private var _isCancelable: Boolean = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogScope = requireNotNull(arguments?.get("dialogScope")) as DialogScope
        _isCancelable = dialogScope.isCancelable
        return dialogScope.build(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = _isCancelable
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    companion object {

        fun show(dialogScope: DialogScope, fragmentManager: FragmentManager) {
            CommonDialogFragment().apply {
                arguments = bundleOf("dialogScope" to dialogScope)
            }.show(fragmentManager, this::class.qualifiedName)
        }
    }
}

fun FragmentActivity.showPopupDialog(block: DialogScope.() -> Unit) =
    CommonDialogFragment.show(DialogScope().apply(block), supportFragmentManager)

fun Fragment.showPopupDialog(block: DialogScope.() -> Unit) =
    CommonDialogFragment.show(DialogScope().apply(block), parentFragmentManager)