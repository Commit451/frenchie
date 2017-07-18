package com.commit451.frenchie

import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test



class ObjectToListAdapterFactoryTest {

    companion object {
        fun createMoshi(): Moshi {
            return Moshi.Builder()
                    .add(ObjectToListAdapterFactory())
                    .build()
        }
    }
    @Test
    fun listTest() {

        val moshi = createMoshi()

        val listJson = Util.getFileText("cats-as-list.json")

        val jsonAdapter = moshi.adapter(CatResponse::class.java)

        val catsResponse = jsonAdapter.fromJson(listJson)

        Assert.assertNotNull(catsResponse)
    }

    @Test
    fun objectTest() {

        val moshi = createMoshi()

        val listJson = Util.getFileText("cats-as-object.json")

        val jsonAdapter = moshi.adapter(CatResponse::class.java)

        val catsResponse = jsonAdapter.fromJson(listJson)

        Assert.assertNotNull(catsResponse)
    }
}