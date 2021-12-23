package com.datarea.omdbapp.binding

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.datarea.omdbapp.R

@BindingAdapter("moviePoster")
fun moviePoster(imageView: AppCompatImageView, url: String?) {
    if (url.isNullOrEmpty()){
        Glide.with(imageView.context)
            .load(R.color.black)
            .timeout(15000)
            .apply {
                RequestOptions()
                    .error(R.drawable.ic_cancel)
                    .fitCenter()
            }
            .fitCenter()
            .into(imageView)
    } else {
        Glide.with(imageView.context)
            .load(url)
            .timeout(15000)
            .apply {
                RequestOptions()
                    .error(R.drawable.ic_cancel)
                    .fitCenter()
            }
            .fitCenter()
            .into(imageView)
    }
}
