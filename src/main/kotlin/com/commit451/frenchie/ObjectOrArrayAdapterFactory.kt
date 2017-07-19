package com.commit451.frenchie


import com.squareup.moshi.*
import java.io.IOException
import java.lang.reflect.Type


class ObjectOrArrayAdapterFactory : JsonAdapter.Factory {

    override fun create(type: Type, annotations: Set<Annotation>?, moshi: Moshi): JsonAdapter<*>? {
        if (annotations != null && annotations.isNotEmpty()) {
            for (annotation in annotations) {
                if (annotation is ObjectOrArrayJsonQualifier) {
                    val rawType = Types.getRawType(type)
                    val collectionElementType = Types.collectionElementType(type, rawType)
                    val objectDelegate: JsonAdapter<Any> = moshi.nextAdapter(this, collectionElementType, Types.nextAnnotations(annotations, ObjectOrArrayJsonQualifier::class.java))
                    val listDelegate: JsonAdapter<Any> = moshi.nextAdapter(this, type, Types.nextAnnotations(annotations, ObjectOrArrayJsonQualifier::class.java))
                    return object : JsonAdapter<Any>() {

                        @Throws(IOException::class)
                        override fun fromJson(reader: JsonReader): Any? {
                            val peek = reader.peek()
                            if (peek == JsonReader.Token.BEGIN_OBJECT) {
                                val value = objectDelegate.fromJson(reader)
                                val list = mutableListOf<Any?>()
                                list.add(value)
                                return list
                            } else {
                                return listDelegate.fromJson(reader)
                            }
                        }

                        @Throws(IOException::class)
                        override fun toJson(writer: JsonWriter?, value: Any?) {
                            listDelegate.toJson(writer, value)
                        }
                    }
                }
            }
        }
        return null
    }
}