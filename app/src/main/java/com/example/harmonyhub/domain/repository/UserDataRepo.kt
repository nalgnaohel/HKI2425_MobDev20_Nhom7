package com.example.harmonyhub.domain.repository

interface UserDataRepo {
    fun getUserName()
    fun setUserName(userName: String, userId: String?)
    fun getAlbums()
    fun getSongs()
    fun getSong()
}