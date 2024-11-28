package com.example.harmonyhub.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.harmonyhub.domain.repository.UserDataRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDataViewModel @Inject constructor(
    private val userRepo: UserDataRepo,
) : ViewModel() {

    private val _userName = MutableLiveData<String?>()
    val userName: LiveData<String?> get() = _userName

    private val _email = MutableLiveData<String?>()
    val email: LiveData<String?> get() = _email

    private val _dataFetchingState = MutableLiveData<DataFetchingState>()
    val dataFetchingState: LiveData<DataFetchingState> get() = _dataFetchingState

    init {
        getUserInfor()
        getAlbums()
    }

    fun getUserInfor() {
        userRepo.getUserInfor { userName, email ->
            _userName.value = userName
            _email.value = email
        }
    }

    fun getAlbums() {
        userRepo.getAlbums(callback = {
            _dataFetchingState.value = it
        })
    }

    fun setAlbum(albumName: String) {
        userRepo.setAlbum(albumName, callback = {
            _dataFetchingState.value = it
        })
    }

    fun resetDataFetchingState() {
        _dataFetchingState.value = DataFetchingState.Pending
    }

}

sealed class DataFetchingState {
    object Pending : DataFetchingState()
    data class Success(val data: Any) : DataFetchingState()
    data class Error(val message: String) : DataFetchingState()
}

