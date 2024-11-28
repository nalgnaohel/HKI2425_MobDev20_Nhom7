package com.example.harmonyhub

import android.app.Application
<<<<<<< HEAD
=======
import com.example.harmonyhub.data.repository.DefaultHomeScreenRepo
import com.example.harmonyhub.data.repository.HomeScreenRepo
>>>>>>> minhnhat_branch
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {
<<<<<<< HEAD
    override fun onCreate() {
        super.onCreate()
=======
    lateinit var container: HomeScreenRepo
    override fun onCreate() {
        super.onCreate()
        container = DefaultHomeScreenRepo()
>>>>>>> minhnhat_branch
    }
}