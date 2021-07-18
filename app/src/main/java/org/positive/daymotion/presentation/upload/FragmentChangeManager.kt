package org.positive.daymotion.presentation.upload

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import org.positive.daymotion.presentation.upload.fragment.CameraFragment
import org.positive.daymotion.presentation.upload.fragment.EditFragment
import org.positive.daymotion.presentation.upload.model.BackgroundSelection

class FragmentChangeManager(private val fragmentManager: FragmentManager) {

    fun updateWithBackgroundSelection(backgroundSelection: BackgroundSelection) {
        when (backgroundSelection) {
            is BackgroundSelection.Camera -> changeToCameraMode()
            is BackgroundSelection.Default -> changeToEditMode()
        }
    }

    private fun findFragment(clazz: Class<out Fragment>) = fragmentManager
        .fragments
        .filterIsInstance(clazz)
        .firstOrNull()

    private fun changeToCameraMode() {
        val cameraFragment = findFragment(CameraFragment::class.java)
        val editFragment = findFragment(EditFragment::class.java)
        fragmentManager.commit {
            editFragment?.let {
                if (!it.isHidden) {
                    hide(it)
                }
            }
            cameraFragment?.let {
                if (it.isHidden) {
                    show(it)
                }
            }
        }
    }

    private fun changeToEditMode() {
        val cameraFragment = findFragment(CameraFragment::class.java)
        val editFragment = findFragment(EditFragment::class.java)
        fragmentManager.commit {
            cameraFragment?.let {
                if (!it.isHidden) {
                    hide(it)
                }
            }
            editFragment?.let {
                if (it.isHidden) {
                    show(it)
                }
            }
        }
    }
}