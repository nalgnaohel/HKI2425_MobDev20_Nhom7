package com.example.harmonyhub.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.harmonyhub.domain.repository.UserDataRepo
import com.example.harmonyhub.ui.components.Song
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val userRepo: UserDataRepo,
): ViewModel() {
    private val _dataFetchingState = MutableLiveData<PlaylistSongFetchingState>()
    val dataFetchingState: MutableLiveData<PlaylistSongFetchingState> get() = _dataFetchingState

    init {
        resetDataFetchingState()
    }

    fun addSongToPlayList(song: Song, playlistName: String) {
        userRepo.addSongToPlayList(song, playlistName, callback = {
            _dataFetchingState.value = it
        })
    }

    fun getPlaylistSongs(playlistName: String) {
        userRepo.getPlaylistSongs(playlistName, callback = {
            _dataFetchingState.value = it
        })
    }

    fun resetDataFetchingState() {
        _dataFetchingState.value = PlaylistSongFetchingState.Pending
    }
}

sealed class PlaylistSongFetchingState {
    object Pending : PlaylistSongFetchingState()
    data class Success(val data: Any) : PlaylistSongFetchingState()
    data class Error(val message: String) : PlaylistSongFetchingState()
}