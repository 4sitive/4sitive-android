package org.positive.daymotion.presentation.upload.model

import java.io.Serializable

data class TextEditConfig(
    val alignment: Alignment,
    val textColor: Int,
    val text: String
) : Serializable {

    companion object {
        val default = TextEditConfig(Alignment.LEFT, 0xFF043EFF.toInt(), "")
    }
}