package com.datarea.omdbapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class OMDPApp: Application() {
    override fun onCreate() {
        super.onCreate()
        if (true) {
            Timber.plant(Timber.DebugTree())
        }
    }
}