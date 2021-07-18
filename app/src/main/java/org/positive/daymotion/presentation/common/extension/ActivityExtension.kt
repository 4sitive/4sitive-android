package org.positive.daymotion.presentation.common.extension

import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun ComponentActivity.registerActivityResult(handleResult: (ActivityResult) -> Unit) =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult(), handleResult)

inline fun <reified T : Fragment> FragmentActivity.findFragment(): T? =
    supportFragmentManager.fragments
        .filterIsInstance(T::class.java)
        .firstOrNull()