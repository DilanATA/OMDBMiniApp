package com.datarea.omdbapp.ui.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import com.datarea.omdbapp.R
import com.datarea.omdbapp.api.model.Movie
import com.datarea.omdbapp.base.BaseFragment
import com.datarea.omdbapp.databinding.FragmentMoviesBinding
import com.datarea.omdbapp.ui.movie.adapter.MovieAdapter
import com.datarea.omdbapp.ui.movie.adapter.OnClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@Suppress("COMPATIBILITY_WARNING")
@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding>(R.layout.fragment_movies), OnClickListener {

    private val movieVM: MovieVM by navGraphViewModels(R.id.nav_graph) {
        defaultViewModelProviderFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onClickForDetail(movie: Movie) {
        TODO("Not yet implemented")
    }
}