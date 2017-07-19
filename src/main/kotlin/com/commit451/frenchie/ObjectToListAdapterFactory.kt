package com.commit451.frenchie


import com.squareup.moshi.*
import java.io.IOException
import java.lang.reflect.Type


class ObjectToListAdapterFactory : JsonAdapter.Factory {

    override fun create(type: Type, annotations: Set<Annotation>?, moshi: Moshi): JsonAdapter<*>? {
        if (annotations != null && annotations.isNotEmpty()) {
            for (annotation in annotations) {
                if (annotation is ObjectToList) {
                    val delegate: JsonAdapter<Any> = moshi.nextAdapter(this, type, Types.nextAnnotations(annotations, ObjectToList::class.java))
                    return object : JsonAdapter<Any>() {

                        @Throws(IOException::class)
                        override fun fromJson(reader: JsonReader): Any? {
                            val peek = reader.peek()
                            if (peek !== JsonReader.Token.BEGIN_ARRAY) {
                                reader.beginArray()
                                val value = delegate.fromJson(reader)
                                reader.endArray()
                                return value
                            } else {
                                return delegate.fromJson(reader)
                            }
                        }

                        @Throws(IOException::class)
                        override fun toJson(writer: JsonWriter?, value: Any?) {
                            delegate.toJson(writer, value)
                        }
                    }
                }
            }
        }
        return null
    }
}