package com.datarea.omdbapp.ui.moviedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.datarea.omdbapp.R
import com.datarea.omdbapp.api.model.Movie
import com.datarea.omdbapp.databinding.FragmentMovieDetailBinding
import com.datarea.omdbapp.util.BundleKeys


class MovieDetailFragment : DialogFragment() {
    lateinit var binding: FragmentMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_detail, container, false)
        binding = FragmentMovieDetailBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireDialog().window?.setWindowAnimations(R.style.DialogAnimation)

        val movie = requireArguments()?.get(BundleKeys.dataDetail) as Movie

        binding.movie = movie
    }

}