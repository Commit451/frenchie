package com.commit451.frenchie

class CatResponse {

    @ObjectToListAdapterFactory.ObjectToList
    lateinit var cats: List<Cat>
}