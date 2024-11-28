package com.example.harmonyhub.domain.repository

import com.example.harmonyhub.presentation.viewmodel.DataFetchingState

interface UserDataRepo {
    fun getUserInfor(callback: (String?, String?) -> Unit)
    fun setUserInfor(userName: String, email: String, userId: String?)
    fun getAlbums(callback: (DataFetchingState) -> Unit)
    fun setAlbum(albumName: String, callback: (DataFetchingState) -> Unit)
    fun getSongs()
    fun getSong()
}