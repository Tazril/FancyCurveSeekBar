package com.taz.customapplication

import com.google.gson.*
import java.lang.reflect.Type

class InterfaceAdapter<T> : JsonSerializer<Any?>,
    JsonDeserializer<Any?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        jsonElement: JsonElement, type: Type,
        jsonDeserializationContext: JsonDeserializationContext
    ): T? {
        val jsonObject = jsonElement.asJsonObject
        val prim =
            jsonObject[CLASSNAME] as JsonPrimitive
        val className = prim.asString
        val klass = getObjectClass(className)
        return jsonDeserializationContext.deserialize(
            jsonObject[DATA],
            klass
        )
    }

    override fun serialize(
        jsonElement: Any?,
        type: Type,
        jsonSerializationContext: JsonSerializationContext
    ): JsonElement {
        val jsonObject = JsonObject()
        jsonObject.addProperty(
            CLASSNAME,
            jsonElement!!.javaClass.name
        )
        jsonObject.add(
            DATA,
            jsonSerializationContext.serialize(jsonElement)
        )
        return jsonObject
    }

    fun getObjectClass(className: String?): Class<*> {
        return try {
            Class.forName(className!!)
        } catch (e: ClassNotFoundException) {
            throw JsonParseException(e.message)
        }
    }

    companion object {
        private const val CLASSNAME = "CLASSNAME"
        private const val DATA = "DATA"
    }
}