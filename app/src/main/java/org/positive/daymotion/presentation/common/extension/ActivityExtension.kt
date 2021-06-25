package org.positive.daymotion.presentation.common.extension

import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts

fun ComponentActivity.registerActivityResult(handleResult: (ActivityResult) -> Unit) =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult(), handleResult)
