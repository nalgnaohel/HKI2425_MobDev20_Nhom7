package com.example.harmonyhub.domain.repository

interface UserDataRepo {
    fun getUserName(callback: (String?) -> Unit)
    fun setUserName(userName: String, email: String, userId: String?)
    fun getAlbums()
    fun getSongs()
    fun getSong()
}