package org.positive.daymotion.presentation.upload.model

import android.view.Gravity

enum class Alignment(val value: Int) {
    LEFT(Gravity.START or Gravity.TOP), CENTER(Gravity.CENTER or Gravity.TOP), RIGHT(Gravity.END or Gravity.TOP)
}