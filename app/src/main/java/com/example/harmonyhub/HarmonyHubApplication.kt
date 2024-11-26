package com.example.harmonyhub

import android.app.Application
import com.example.harmonyhub.data.repository.DefaultHomeScreenRepo
import com.example.harmonyhub.data.repository.HomeScreenRepo

class HarmonyHubApplication : Application() {

    lateinit var container: HomeScreenRepo

    override fun onCreate() {
        super.onCreate()
        container = DefaultHomeScreenRepo()
    }
}