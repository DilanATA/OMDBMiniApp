package com.datarea.omdbapp.helper

import com.datarea.omdbapp.util.Constants.API_KEY

fun queryMap(title: String) : Map<String, String>{
    val map = HashMap<String, String>()
    map["t"] = title
    map["apikey"] = API_KEY
    return map
}