package com.datarea.omdbapp.api

import com.datarea.omdbapp.api.model.Movie
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface OMDBApi {
//    https://omdbapi.com/?t=starwars&apikey=2c2f05a

    @GET("/?")
    suspend fun getMovieFilter(
        @QueryMap map: Map<String, String>
    ): Movie

}