package com.example.harmonyhub.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.harmonyhub.domain.repository.UserDataRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FriendListViewModel @Inject constructor(
    private val userRepo: UserDataRepo,
): ViewModel() {

    private val _dataFetchingState = MutableLiveData<FriendListFetchingState>()
    val dataFetchingState: MutableLiveData<FriendListFetchingState> get() = _dataFetchingState

    init {
        resetDataFetchingState()
    }

    fun resetDataFetchingState() {
        _dataFetchingState.value = FriendListFetchingState.Pending
    }

    fun searchForEmail(email: String) {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"

        if (!email.matches(emailPattern.toRegex())) {
            _dataFetchingState.value = FriendListFetchingState.Error("Invalid email format")
            return
        }

        userRepo.searchForEmail(email) { state ->
            _dataFetchingState.value = state
        }
    }

    fun getFriendRequests() {
        userRepo.getFriendRequests { state ->
            _dataFetchingState.value = state
        }
    }

    fun acceptFriendRequest(uid: String) {
        userRepo.acceptFriendRequest(uid) { state ->
            _dataFetchingState.value = state
        }
    }

    fun declineFriendRequest(uid: String) {
        userRepo.declineFriendRequest(uid) { state ->
            _dataFetchingState.value = state
        }
    }
}

sealed class FriendListFetchingState {
    object Pending : FriendListFetchingState()
    data class Success(val data: Any) : FriendListFetchingState()
    data class Error(val message: String) : FriendListFetchingState()
}