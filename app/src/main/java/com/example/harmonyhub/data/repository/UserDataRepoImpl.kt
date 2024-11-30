package com.example.harmonyhub.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.harmonyhub.domain.repository.UserDataRepo
import com.example.harmonyhub.presentation.viewmodel.DataFetchingState
import com.example.harmonyhub.presentation.viewmodel.FavoriteSongFetchingState
import com.example.harmonyhub.ui.components.Song
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class UserDataRepoImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
): UserDataRepo {

    fun getUserDataRef(userId: String?): DocumentReference {
        val userRef = firestore.collection("users").document(userId.toString())
        return userRef
    }

    fun getAlbumsRef(userId: String?): CollectionReference {
        val albumsRef = firestore.collection("users").document(userId.toString()).collection("albums")
        return albumsRef
    }

    fun getAlbumRef(userId: String?, albumName: String): DocumentReference {
        val albumRef = getAlbumsRef(userId).document(albumName)
        return albumRef
    }

    fun getFavoriteSongRef(userId: String?, url: String): DocumentReference {
        val favoriteSongsRef = firestore.collection("users").document(userId.toString()).collection("favorite").document(url)
        return favoriteSongsRef
    }

    override fun getUserInfor(callback: (String?, String?) -> Unit) {
        val userId = auth.currentUser?.uid
        val userRef = getUserDataRef(userId)

        userRef.get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {

                    val username = document.getString("userName")
                    val email = document.getString("email")

                    Log.d("OwO", "DocumentSnapshot data: ${username}")
                    callback(username, email)
                } else {
                    Log.d("OwO", "No such document")
                    callback(null, null)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
                callback(null, null)
            }
    }

    override fun setUserInfor(userName: String, email: String, userId: String?) {
        val userMap = hashMapOf(
            "userName" to userName,
            "email" to email
        )

        val userRef = getUserDataRef(userId)

        userRef.set(userMap)
            .addOnSuccessListener() {
                 Log.d(TAG, "DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error writing document", e)
            }
    }

    override fun getAlbums(callback: (DataFetchingState) -> Unit) {
        val userId = auth.currentUser?.uid
        val albumsRef = getAlbumsRef(userId)

        albumsRef.get()
            .addOnSuccessListener { result ->
                val albums = mutableListOf<String?>()
                for (document in result) {
                    Log.d("album", "${document.id} => ${document.data}")
                    albums.add(document.getString("albumName"))
                }
                callback(DataFetchingState.Success(albums))
            }
            .addOnFailureListener { exception ->
                Log.d("album", "Error getting documents: ", exception)
                callback(DataFetchingState.Error("Failed to get albums"))
            }
    }

    override fun setAlbum(albumName: String, callback: (DataFetchingState) -> Unit) {
        val userId = auth.currentUser?.uid
        val albumRef = getAlbumRef(userId, albumName)

        val albumMap = hashMapOf(
            "albumName" to albumName
        )

        albumRef.set(albumMap)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully written!")
                getAlbums(callback)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error writing document", e)
                callback(DataFetchingState.Error("Failed to add album"))
            }
    }

    override fun addFavoriteSong(song: Song) {
        val userId = auth.currentUser?.uid

        Log.d("uid", userId.toString())
        Log.d("url", song.url)

        val encodedUrl = URLEncoder.encode(song.url, StandardCharsets.UTF_8.toString())

        val favoriteSongRef = getFavoriteSongRef(userId, encodedUrl)

        val songMap = hashMapOf(
                "songName" to song.name,
                "artist" to song.artist,
                "imageResId" to song.imageResId,
                "url" to song.url
            )

            favoriteSongRef.set(songMap)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot successfully written!")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error writing document", e)
                }
    }

    override fun removeFavoriteSong(
        song: Song,
        callback: (FavoriteSongFetchingState) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getFavoriteSongs(callback: (FavoriteSongFetchingState) -> Unit) {
        TODO("Not yet implemented")
    }
}