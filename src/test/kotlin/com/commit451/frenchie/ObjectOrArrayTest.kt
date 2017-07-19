package com.commit451.frenchie

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test


class ObjectOrArrayTest {

    companion object {
        fun createMoshi(): Moshi {
            return Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .add(ObjectOrArrayAdapterFactory())
                    .build()
        }
    }

    @Test
    fun listTest() {

        val moshi = createMoshi()

        val listJson = Util.getFileText("cats-as-list.json")

        val jsonAdapter = moshi.adapter(CatResponse::class.java)

        val catsResponse = jsonAdapter.fromJson(listJson)!!

        Assert.assertNotNull(catsResponse)
        Assert.assertTrue(catsResponse.cats.size == 2)
        Assert.assertTrue(catsResponse.cats[1].name == "Lovelace")
    }

    @Test
    fun objectTest() {

        val moshi = createMoshi()

        val listJson = Util.getFileText("cats-as-object.json")

        val jsonAdapter = moshi.adapter(CatResponse::class.java)

        val catsResponse = jsonAdapter.fromJson(listJson)!!

        Assert.assertNotNull(catsResponse)
        Assert.assertTrue(catsResponse.cats.size == 1)
        Assert.assertTrue(catsResponse.cats.first().name == "Sputnik")
    }
}