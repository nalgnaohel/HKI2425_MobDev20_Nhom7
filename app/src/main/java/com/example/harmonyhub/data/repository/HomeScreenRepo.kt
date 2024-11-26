package com.example.harmonyhub.data.repository

import com.example.harmonyhub.data.network.ResponseHomeScreenData

interface HomeScreenRepo {
    suspend fun updatePopularItem() : ResponseHomeScreenData?
}