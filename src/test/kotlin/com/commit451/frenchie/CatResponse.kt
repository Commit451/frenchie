package com.commit451.frenchie

class CatResponse {

    @ObjectOrArrayJsonQualifier
    lateinit var cats: List<Cat>
}