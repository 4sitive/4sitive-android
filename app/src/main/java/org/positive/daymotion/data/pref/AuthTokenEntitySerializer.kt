package org.positive.daymotion.data.pref

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import org.positive.daymotion.datastore.AuthTokenEntity
import java.io.InputStream
import java.io.OutputStream

class AuthTokenEntitySerializer : Serializer<AuthTokenEntity> {

    override val defaultValue: AuthTokenEntity = AuthTokenEntity.getDefaultInstance()

    override fun readFrom(
        input: InputStream
    ): AuthTokenEntity = try {
        AuthTokenEntity.parseFrom(input)
    } catch (exception: InvalidProtocolBufferException) {
        throw CorruptionException("Cannot read proto.", exception)
    }

    override fun writeTo(t: AuthTokenEntity, output: OutputStream) = t.writeTo(output)
}