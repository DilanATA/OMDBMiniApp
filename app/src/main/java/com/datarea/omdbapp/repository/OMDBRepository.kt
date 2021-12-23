package com.datarea.omdbapp.repository

import com.datarea.omdbapp.api.OMDBApi
import com.datarea.omdbapp.api.model.Movie
import com.datarea.omdbapp.data.Resource
import com.github.ajalt.timberkt.i
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

import javax.inject.Inject

class OMDBRepository @Inject constructor(
    private val omdbApi: OMDBApi
) {
    fun getMovieFilter( map: Map<String, String>): Flow<Resource<Movie>> {
        return flow{
            emit(Resource.loading(null))
            val movie = omdbApi.getMovieFilter(
                map= map
            )
            emit(Resource.success(movie))
        }.retryWhen { cause, attempt ->
            i { "attempt count -> $attempt" }
            i { "cause -> $cause" }
            (cause is Exception).also { if (it) delay(10_000) }
        }.catch {
            emit(Resource.error(it.localizedMessage, null, it))
        }.flowOn(Dispatchers.IO)
    }
}