package com.example.harmonyhub.data.repository

import com.example.harmonyhub.data.network.ResponseHomeScreenData
import com.example.harmonyhub.data.network.ResponseSplit

interface HomeScreenRepo {
    suspend fun updatePopularItem() : ResponseHomeScreenData?
    suspend fun splitMusic(music1 : String) : ResponseSplit?

}