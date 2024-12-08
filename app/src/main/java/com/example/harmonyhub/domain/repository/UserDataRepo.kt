package com.example.harmonyhub.domain.repository

import com.example.harmonyhub.presentation.viewmodel.DataFetchingState
import com.example.harmonyhub.presentation.viewmodel.FavoriteSongFetchingState
import com.example.harmonyhub.presentation.viewmodel.PlaylistSongFetchingState
import com.example.harmonyhub.ui.components.Song

interface UserDataRepo {
    fun getUserInfor(callback: (String?, String?) -> Unit)
    fun setUserInfor(userName: String, email: String, userId: String?)

    fun getAlbums(callback: (DataFetchingState) -> Unit)
    fun setAlbum(albumName: String, callback: (DataFetchingState) -> Unit)

    fun addFavoriteSong(song: Song, callback: (FavoriteSongFetchingState) -> Unit)
    fun removeFavoriteSong(song: Song, callback: (FavoriteSongFetchingState) -> Unit)
    fun getFavoriteSongs(callback: (FavoriteSongFetchingState) -> Unit)

    fun addSongToPlayList(song: Song, playlistName: String, callback: (PlaylistSongFetchingState) -> Unit)
    fun getPlaylistSongs(playlistName: String, callback: (PlaylistSongFetchingState) -> Unit)
//    fun removeSongFromPlayList()
//    fun getSongFromPlayList()
//    fun deletePlayList()
}