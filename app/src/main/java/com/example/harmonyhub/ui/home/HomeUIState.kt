package com.example.harmonyhub.ui.home

import com.example.harmonyhub.data.network.AlbumOut
import com.example.harmonyhub.data.network.ArtistOut
import com.example.harmonyhub.data.network.ResponseHomeScreenData
import com.example.harmonyhub.ui.components.Song

sealed interface HomeUIState {
    object Loading : HomeUIState
    object Error : HomeUIState
    data class Success(val popularItem: ResponseHomeScreenData) : HomeUIState
}
