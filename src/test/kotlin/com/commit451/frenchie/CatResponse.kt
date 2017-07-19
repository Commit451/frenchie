package com.commit451.frenchie

class CatResponse {

    @ObjectToList
    lateinit var cats: List<Cat>
}