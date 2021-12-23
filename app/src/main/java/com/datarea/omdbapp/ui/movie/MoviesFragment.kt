package com.datarea.omdbapp.ui.movie

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@Suppress("COMPATIBILITY_WARNING")
@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding>(R.layout.fragment_movies),
    OnClickListener {

    var movieList: ArrayList<Movie>? = arrayListOf()
    lateinit var movieAdapter: MovieAdapter
    val bundle = Bundle()


    private val movieVM: MovieVM by navGraphViewModels(R.id.nav_graph) {
        defaultViewModelProviderFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchView()
    }

    private fun observeMovie(text: String) {
        scope.launch {
            movieVM.getMovieByFilter(
                map = queryMap(
                    title = text
                )
            )
            movieVM.movie.observe(viewLifecycleOwner, {
                when (it.status) {
                    Status.SUCCESS -> {
                        movieList?.clear()
                        movieList?.add(it.data!!)
                        if (movieList.isNullOrEmpty()) {
                            i{"dilan"}
                            makeToast("No match found!")
                        } else {
                            movieAdapter =
                                MovieAdapter(requireContext(), movieList!!, this@MoviesFragment)
                            movieAdapter.notifyDataSetChanged()

                            binding.rvMovies.adapter = movieAdapter
                            binding.progressBar.hide()
                        }
                    }
                    Status.ERROR -> i { "error ${it.throwable}" }
                    Status.LOADING -> i { "Loading" }
                }
            })
        }
    }

    private fun clearList() {
        movieList?.clear()
        movieAdapter = MovieAdapter(requireContext(), movieList!!, this@MoviesFragment)
        binding.rvMovies.adapter = movieAdapter
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                clearList()
                search(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                clearList()
                search(newText)
                return true
            }
        })
    }

    private fun search(text: String?) {
        binding.progressBar.show()
        text?.let {
            observeMovie(text)
        }
    }

    override fun onClickForDetail(movie: Movie) {
        bundle.putParcelable(BundleKeys.dataDetail, movie)
        navigateSafe(R.id.action_moviesFragment_to_movieDetailFragment, bundle)
    }
}