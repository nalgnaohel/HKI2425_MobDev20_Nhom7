package com.example.harmonyhub.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.harmonyhub.domain.repository.UserDataRepo
import com.example.harmonyhub.ui.components.Song
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class FavoriteSongsViewModel @Inject constructor(
    private val userRepo: UserDataRepo
) : ViewModel() {

    private val _dataFetchingState = MutableLiveData<FavoriteSongFetchingState?>()
    val dataFetchingState: MutableLiveData<FavoriteSongFetchingState?> get() = _dataFetchingState

    fun resetDataFetchingState() {
        _dataFetchingState.value = FavoriteSongFetchingState.Pending
    }

    init {
        _dataFetchingState.value = FavoriteSongFetchingState.Pending
    }

    fun addFavoriteSong(song: Song) {
        Log.d("OwO", "Adding favorite song: ${song.name}")
        userRepo.addFavoriteSong(song, callback = {
            _dataFetchingState.value = it
        })
    }
}

sealed class FavoriteSongFetchingState {
    object Pending : FavoriteSongFetchingState()
    data class Success(val data: Any) : FavoriteSongFetchingState()
    data class Error(val message: String) : FavoriteSongFetchingState()
}