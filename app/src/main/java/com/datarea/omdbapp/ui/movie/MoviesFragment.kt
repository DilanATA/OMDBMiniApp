package com.datarea.omdbapp.ui.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.navigation.navGraphViewModels
import com.datarea.omdbapp.R
import com.datarea.omdbapp.api.model.Movie
import com.datarea.omdbapp.base.BaseFragment
import com.datarea.omdbapp.data.Status
import com.datarea.omdbapp.databinding.FragmentMoviesBinding
import com.datarea.omdbapp.extension.hide
import com.datarea.omdbapp.extension.makeToast
import com.datarea.omdbapp.extension.navigateSafe
import com.datarea.omdbapp.extension.show
import com.datarea.omdbapp.helper.queryMap
import com.datarea.omdbapp.ui.movie.adapter.MovieAdapter
import com.datarea.omdbapp.ui.movie.adapter.OnClickListener
import com.datarea.omdbapp.util.BundleKeys
import com.github.ajalt.timberkt.i
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@Suppress("COMPATIBILITY_WARNING")
@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding>(R.layout.fragment_movies), OnClickListener {

    lateinit var movieList: ArrayList<Movie>
    lateinit var movieAdapter: MovieAdapter
    val bundle = Bundle()


    private val movieVM: MovieVM by navGraphViewModels(R.id.nav_graph) {
        defaultViewModelProviderFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchView()
    }

    private fun observeMovie(text: String){
        scope.launch {
            movieList.clear()
            movieVM.getMovieByFilter(
                map = queryMap(
                    title = text
                )
            )

            movieVM.movie.observe(viewLifecycleOwner, {
                when (it.status) {
                    Status.SUCCESS -> {
                        movieList.add(it.data!!)
                        movieAdapter = MovieAdapter(requireContext(), movieList, this@MoviesFragment)
                        movieAdapter.notifyDataSetChanged()

                        binding.rvMovies.adapter = movieAdapter
                        binding.progressBar.hide()
                    }
                    Status.ERROR -> i { "error ${it.throwable}" }
                    Status.LOADING -> i { "Loading" }
                }
            })
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                search(newText)
                return true
            }
        })
    }

    private fun search(text: String?) {
        movieList = arrayListOf()
        binding.progressBar.show()

        text?.let{
            observeMovie(text)
                movieList.let {
                if(movieList.isNullOrEmpty()){
                    makeToast("No match found!")
                }
            }
        }

    }

    override fun onClickForDetail(movie: Movie) {
        bundle.putParcelable(BundleKeys.dataDetail, movie)
        navigateSafe(R.id.action_moviesFragment_to_movieDetailFragment, bundle)
    }
}