package com.example.harmonyhub.data

import com.example.harmonyhub.ui.components.Song

data class HomeUIState(
    val listPopularArtist : MutableList<ArtistOut>? = mutableListOf(),
    val listPopularAlbums: MutableList<AlbumOut>? = mutableListOf(),
    val listRecommendSong: MutableList<Song>? = mutableListOf(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
