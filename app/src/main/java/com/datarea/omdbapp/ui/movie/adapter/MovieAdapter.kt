package com.datarea.omdbapp.ui.movie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.datarea.omdbapp.R
import com.datarea.omdbapp.api.model.Movie
import com.datarea.omdbapp.databinding.ItemMovieBinding

class MovieAdapter(
    private val context: Context,
    private var movieList: ArrayList<Movie>,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<MovieAdapter.MovieHolder>(){

    inner class MovieHolder(val itemMovieBinding: ItemMovieBinding): RecyclerView.ViewHolder(
        itemMovieBinding.root
    ){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder =
        MovieHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_movie,
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.itemMovieBinding.movie = movieList[position]

        holder.itemMovieBinding.llMovie.setOnClickListener {
            onClickListener.onClickForDetail(movieList[position])
        }
    }

    override fun getItemCount(): Int = movieList.size
}

interface OnClickListener {
    fun onClickForDetail(movie: Movie)
}