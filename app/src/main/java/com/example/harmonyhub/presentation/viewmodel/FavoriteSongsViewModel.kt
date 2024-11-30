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
    private val _dataFetchingState = MutableLiveData<DataFetchingState>()
    val dataFetchingState: MutableLiveData<DataFetchingState> get() = _dataFetchingState

    fun addFavoriteSong(song: Song) {
        Log.d("OwO", "Adding favorite song: ${song.name}")
        userRepo.addFavoriteSong(song)
    }
}

sealed class FavoriteSongFetchingState {
    object Pending : FavoriteSongFetchingState()
    data class Success(val data: Any) : FavoriteSongFetchingState()
    data class Error(val message: String) : FavoriteSongFetchingState()
}