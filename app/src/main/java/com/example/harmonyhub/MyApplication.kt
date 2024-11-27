package com.example.harmonyhub

import android.app.Application
import com.example.harmonyhub.data.repository.DefaultHomeScreenRepo
import com.example.harmonyhub.data.repository.HomeScreenRepo
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {
    lateinit var container: HomeScreenRepo
    override fun onCreate() {
        super.onCreate()
        container = DefaultHomeScreenRepo()
    }
}