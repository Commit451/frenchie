package com.commit451.frenchie


import com.squareup.moshi.*
import java.io.IOException
import java.lang.reflect.Type

/**
 * Parses fields as either a single object or an array of objects. Commonly found with confused APIs
 */
class ObjectOrArrayAdapterFactory : JsonAdapter.Factory {

    override fun create(type: Type, annotations: Set<Annotation>?, moshi: Moshi): JsonAdapter<*>? {
        if (annotations != null && annotations.isNotEmpty()) {
            for (annotation in annotations) {
                if (annotation is ObjectOrArray) {
                    val rawType = Types.getRawType(type)
                    val collectionElementType = Types.collectionElementType(type, rawType)
                    val objectDelegate: JsonAdapter<Any> = moshi.nextAdapter(this, collectionElementType, Types.nextAnnotations(annotations, ObjectOrArray::class.java))
                    val listDelegate: JsonAdapter<Any> = moshi.nextAdapter(this, type, Types.nextAnnotations(annotations, ObjectOrArray::class.java))
                    return object : JsonAdapter<Any>() {

                        @Throws(IOException::class)
                        override fun fromJson(reader: JsonReader): Any? {
                            val peek = reader.peek()
                            if (peek == JsonReader.Token.BEGIN_OBJECT) {
                                //delegate out to the adapter that can read the object
                                val value = objectDelegate.fromJson(reader)
                                val list = mutableListOf<Any?>()
                                list.add(value)
                                return list
                            } else {
                                //delegate out to the adapter that can read the list
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