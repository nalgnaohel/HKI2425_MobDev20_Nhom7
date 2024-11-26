package com.example.harmonyhub.domain.repository

interface UserDataRepo {
    fun getUserInfor(callback: (String?, String?) -> Unit)
    fun setUserInfor(userName: String, email: String, userId: String?)
    fun getAlbums()
    fun getSongs()
    fun getSong()
}