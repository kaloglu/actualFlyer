package com.kaloglu.actualflayer.database

import com.tencent.mmkv.MMKV
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json

class KeyValueStorage(
    val mmkv: MMKV,
    private val json: Json
) {
    fun <T> put(key: String, value: T, serializer: SerializationStrategy<T>): Boolean {
        return mmkv.encode(key, json.encodeToString(serializer, value))
    }

    fun <T> get(key: String, serializer: DeserializationStrategy<T>): T? {
        return mmkv.decodeString(key)?.let { json.decodeFromString(serializer, it) }
    }

    fun put(key: String, value: Any): Boolean {
        return when (value) {
            is Boolean -> mmkv.encode(key, value)
            is Int -> mmkv.encode(key, value)
            is Long -> mmkv.encode(key, value)
            is Float -> mmkv.encode(key, value)
            is Double -> mmkv.encode(key, value)
            is String -> mmkv.encode(key, value)
            is ByteArray -> mmkv.encode(key, value)
            else -> throw IllegalArgumentException(
                "SerializationStrategy should be provided " +
                        "for class type ${value.javaClass.canonicalName}"
            )
        }
    }

    inline fun <reified T> get(key: String): T? {
        return when (T::class) {
            Boolean::class -> mmkv.decodeBool(key) as T
            Int::class -> mmkv.decodeInt(key) as T
            Long::class -> mmkv.decodeLong(key) as T
            Float::class -> mmkv.decodeFloat(key) as T
            Double::class -> mmkv.decodeDouble(key) as T
            String::class -> mmkv.decodeString(key) as T
            ByteArray::class -> mmkv.decodeBytes(key) as T
            else -> throw IllegalArgumentException(
                "DeserializationStrategy should be provided " +
                        "for class type ${T::class.javaClass.canonicalName}"
            )
        }
    }

    fun remove(key: String) = mmkv.removeValueForKey(key)

    fun removeValuesForKeys(vararg keys: String) = mmkv.removeValuesForKeys(keys)
}