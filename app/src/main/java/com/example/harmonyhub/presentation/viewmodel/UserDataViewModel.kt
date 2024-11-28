package com.example.harmonyhub.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.harmonyhub.domain.repository.UserDataRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class UserDataViewModel @Inject constructor(
    private val userRepo: UserDataRepo,
) : ViewModel() {

    private val _userName = MutableLiveData<String?>()
    open val userName: LiveData<String?> get() = _userName

    private val _email = MutableLiveData<String?>()
    open val email: LiveData<String?> get() = _email

    init {
        getUserInfor()
    }

    fun getUserInfor() {
        userRepo.getUserInfor { userName, email ->
            _userName.value = userName
            _email.value = email
        }
    }
}

