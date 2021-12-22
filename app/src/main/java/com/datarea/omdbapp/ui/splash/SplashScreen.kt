package com.datarea.omdbapp.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.datarea.omdbapp.R
import com.datarea.omdbapp.base.BaseFragment
import com.datarea.omdbapp.databinding.FragmentSplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@Suppress("COMPATIBILITY_WARNING")
@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class SplashScreen : BaseFragment<FragmentSplashScreenBinding>(R.layout.fragment_splash_screen) {

    var connected: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}