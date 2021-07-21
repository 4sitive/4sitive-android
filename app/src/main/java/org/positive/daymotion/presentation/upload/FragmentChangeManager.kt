package org.positive.daymotion.presentation.upload

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import org.positive.daymotion.presentation.upload.model.Mode

class FragmentChangeManager(private val fragmentManager: FragmentManager) {

    fun change(old: Mode, new: Mode) {
        fragmentManager.commit {
            val oldFragment = findFragment(old.fragmentClazz)
            val newFragment = findFragment(new.fragmentClazz)
            oldFragment?.let {
                hide(it)
            }
            newFragment?.let {
                show(it)
            }
        }
    }

    private fun findFragment(clazz: Class<out Fragment>) = fragmentManager
        .fragments
        .filterIsInstance(clazz)
        .firstOrNull()
}