package org.positive.daymotion.extension

import com.google.gson.annotations.SerializedName
import java.lang.reflect.Field

fun Enum<*>.declaredSerializedName(): String {
    val field: Field = javaClass.getDeclaredField(name)
    val sName = field.getAnnotation(SerializedName::class.java)
    return sName.value
}