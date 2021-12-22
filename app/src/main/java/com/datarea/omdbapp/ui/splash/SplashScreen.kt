package com.datarea.omdbapp.ui.splash

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.datarea.omdbapp.R
import com.datarea.omdbapp.base.BaseFragment
import com.datarea.omdbapp.databinding.FragmentSplashScreenBinding
import com.datarea.omdbapp.extension.makeToast
import com.datarea.omdbapp.extension.navigateSafe
import com.github.ajalt.timberkt.i
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@Suppress("COMPATIBILITY_WARNING")
@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class SplashScreen : BaseFragment<FragmentSplashScreenBinding>(R.layout.fragment_splash_screen) {

    var connected: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startUpdates()

        if(!connected) {
            navigateSafe(R.id.action_splashScreen_to_moviesFragment)
        } else {
            makeToast("Please check your internet connection")
        }
    }

    private fun startUpdates() {
        scope.launch(Dispatchers.Main) {
            while (true) {
                connected =hasInternetConnection()
                i{"connected $connected"}
                delay(10000)
            }
        }
    }

    fun hasInternetConnection(): Boolean {
        try {
            val conMgr =
                requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return conMgr.activeNetworkInfo != null && conMgr.activeNetworkInfo!!.isAvailable && conMgr.activeNetworkInfo!!
                .isConnected
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun stopUpdates() {
        scope.cancel()
    }
    override fun onDestroy() {
        super.onDestroy()
        stopUpdates()
    }
}