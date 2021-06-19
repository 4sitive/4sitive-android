package org.positive.daymotion.common

import android.app.Activity
import androidx.fragment.app.Fragment
import kotlin.reflect.KProperty

fun <B> Activity.bundle(
    key: String? = null
) = BundleValueHolder { propertyName ->
    @Suppress("UNCHECKED_CAST")
    intent.extras?.get(key ?: propertyName) as B
}

fun <B> Fragment.bundle(key: String? = null) =
    BundleValueHolder { propertyName ->
        @Suppress("UNCHECKED_CAST")
        arguments?.get(key ?: propertyName) as B
    }


class BundleValueHolder<V>(
    private var initializer: (String) -> V
) {
    @Volatile
    private var _value: Any? = UninitializedValue
    private val lock = this

    operator fun getValue(thisRef: Any?, property: KProperty<*>): V {
        val v1 = _value
        if (v1 !== UninitializedValue) {
            @Suppress("UNCHECKED_CAST")
            return v1 as V
        }

        return synchronized(lock) {
            val v2 = _value
            if (v2 !== UninitializedValue) {
                @Suppress("UNCHECKED_CAST") (v2 as V)
            } else {
                @Suppress("UNCHECKED_CAST")
                val typedValue = initializer(property.name)
                _value = typedValue
                typedValue
            }
        }
    }

    companion object {
        private object UninitializedValue
    }
}