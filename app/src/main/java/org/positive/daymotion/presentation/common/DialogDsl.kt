package org.positive.daymotion.presentation.common

import android.app.Dialog
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import kotlinx.parcelize.Parcelize
import org.positive.daymotion.R
import org.positive.daymotion.databinding.WidgetCommonDialogBinding
import org.positive.daymotion.presentation.common.extension.layoutInflater
import java.io.Serializable


class DialogScope : Serializable {

    var title: String = ""

    var content: String = ""

    var blueButtonText: String = "확인"

    var grayButtonText: String = "취소"

    var isVisibleBlueButton: Boolean = true

    var isVisibleGrayButton: Boolean = false

    var isCancelable: Boolean = false

    private var onClickBlueButton: () -> Unit = {}

    private var onClickGrayButton: () -> Unit = {}

    fun onClickBlueButton(block: () -> Unit) {
        onClickBlueButton = block
    }

    fun onClickGrayButton(block: () -> Unit) {
        onClickGrayButton = block
    }

    fun buildCommonDialogBundle() = CommonDialogBundle(
        title = title,
        content = content,
        blueButtonText = blueButtonText,
        grayButtonText = grayButtonText,
        isVisibleBlueButton = isVisibleBlueButton,
        isVisibleGrayButton = isVisibleGrayButton,
        isCancelable = isCancelable,
        onClickBlueButton = onClickBlueButton,
        onClickGrayButton = onClickGrayButton
    )
}

@Parcelize
data class CommonDialogBundle(
    val title: String,
    val content: String,
    val blueButtonText: String,
    val grayButtonText: String,
    val isVisibleBlueButton: Boolean,
    val isVisibleGrayButton: Boolean,
    val isCancelable: Boolean,
    val onClickBlueButton: () -> Unit,
    val onClickGrayButton: () -> Unit
) : Parcelable

class CommonDialogFragment : DialogFragment() {

    private val commonDialogBundle by bundle<CommonDialogBundle>()
    private val handler by lazy { Handler() }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = requireContext()
        val binding = WidgetCommonDialogBinding.inflate(context.layoutInflater, null, false)

        val dialog = AlertDialog.Builder(context, R.style.CommonDialogTheme)
            .setView(binding.root)
            .create()

        binding.commonDialogBundle = commonDialogBundle
        binding.handler = handler
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = commonDialogBundle.isCancelable
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    inner class Handler {
        fun onClickBlueButton() {
            commonDialogBundle.onClickBlueButton()
            dialog?.dismiss()
        }

        fun onClickGrayButton() {
            commonDialogBundle.onClickGrayButton()
            dialog?.dismiss()
        }
    }

    companion object {

        fun show(block: DialogScope.() -> Unit, fragmentManager: FragmentManager) {
            val dialogScope = DialogScope()
            dialogScope.block()

            CommonDialogFragment().apply {
                arguments = bundleOf(
                    "commonDialogBundle" to dialogScope.buildCommonDialogBundle()
                )
            }.show(fragmentManager, this::class.qualifiedName)
        }
    }
}

fun FragmentActivity.showPopupDialog(block: DialogScope.() -> Unit) =
    CommonDialogFragment.show(block, supportFragmentManager)

fun Fragment.showPopupDialog(block: DialogScope.() -> Unit) =
    CommonDialogFragment.show(block, parentFragmentManager)