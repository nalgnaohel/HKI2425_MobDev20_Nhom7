package com.example.harmonyhub.presentation.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.harmonyhub.domain.repository.UserDataRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val userRepo: UserDataRepo,
) : ViewModel() {

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {
        if (auth.currentUser == null) {
             _authState.value = AuthState.Unauthenticated
        } else {
             _authState.value = AuthState.Authenticated
        }
    }

    fun login(email: String, password: String) {

        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email and password must not be empty")
            return
        }

        _authState.value = AuthState.Loading

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user?.isEmailVerified == true && user != null) {
                        _authState.value = AuthState.Authenticated
                    } else {
                        auth.signOut()
                        _authState.value = AuthState.Error("Email not verified. Please check your email verification link.")
                    }
                } else {

                    _authState.value = AuthState.Error(task.exception?.message ?: "An unknown error occurred")
                }
            }
    }

    fun signup(email: String, password: String, username: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email and password must not be empty")
            return
        }

        _authState.value = AuthState.Loading

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                _authState.value = AuthState.EmailNotVerified
                                userRepo.setUserName(username, user.uid)
                            } else {
                                _authState.value = AuthState.Error(task.exception?.message ?: "Failed to send verification email")
                            }
                        }
                    auth.signOut()
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "An unknown error occurred")
                }
            }
    }

    fun signOut() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }

    fun resetPassword(email: String) {
        if (email.isEmpty()) {
            _authState.value = AuthState.Error("Email must not be empty")
            return
        }

        _authState.value = AuthState.Loading

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Unauthenticated
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "An unknown error occurred")
                }
            }
    }
}

sealed class AuthState {
    object EmailNotVerified : AuthState()
    object Unauthenticated : AuthState()
    object Authenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}