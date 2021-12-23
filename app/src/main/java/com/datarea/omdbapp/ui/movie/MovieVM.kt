package com.datarea.omdbapp.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.datarea.omdbapp.api.model.Movie
import com.datarea.omdbapp.data.Resource
import com.datarea.omdbapp.repository.OMDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieVM @Inject constructor(
    private val omdbRepository: OMDBRepository
): ViewModel() {

    private val _movie = MutableLiveData<Resource<Movie>>()
    val movie : LiveData<Resource<Movie>>
    get() = _movie
    fun getMovieByFilter( map: Map<String, String>) {
        viewModelScope.launch {
            omdbRepository.getMovieFilter(map= map).collect {
                _movie.postValue(it)
            }
        }
    }
}