package org.positive.sms.di

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import kotlin.jvm.internal.Reflection
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

class NullableTypeAdapterFactory : TypeAdapterFactory {

    override fun <T : Any> create(
        gson: Gson,
        type: TypeToken<T>
    ): TypeAdapter<T>? = if (isKotlinClassType(type)) {
        NullableTypAdapter<T>(gson.getDelegateAdapter(this, type), type)
    } else {
        null
    }

    private fun isKotlinClassType(
        typeToken: TypeToken<*>
    ): Boolean = typeToken.rawType.declaredAnnotations.any {
        it.annotationClass == Metadata::class
    }

    private class NullableTypAdapter<T>(
        private val delegate: TypeAdapter<T>,
        private val type: TypeToken<*>
    ) : TypeAdapter<T>() {

        override fun write(out: JsonWriter, value: T?) = delegate.write(out, value)

        override fun read(input: JsonReader): T? {
            val value = delegate.read(input)
            if (value != null) {
                val properties = Reflection.createKotlinClass(type.rawType).memberProperties
                properties.forEach {
                    if (!it.returnType.isMarkedNullable && it.call(value) == null) {
                        throwNonNullMemberException(it)
                    }
                }
            }
            return value
        }

        private fun throwNonNullMemberException(kProperty: KProperty1<*, *>) {
            throw JsonParseException(
                "Cannot value of non-nullable member [${kProperty.name}] be null"
            )
        }
    }
}