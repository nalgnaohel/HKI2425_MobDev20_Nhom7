package com.example.harmonyhub.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.harmonyhub.domain.repository.FirebaseUser
import com.example.harmonyhub.domain.repository.UserDataRepo
import com.example.harmonyhub.presentation.viewmodel.DataFetchingState
import com.example.harmonyhub.presentation.viewmodel.FavoriteSongFetchingState
import com.example.harmonyhub.presentation.viewmodel.FriendListFetchingState
import com.example.harmonyhub.presentation.viewmodel.PlaylistSongFetchingState
import com.example.harmonyhub.ui.components.Song
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import kotlin.text.set

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

    fun getFavoriteSongsRef(userId: String?): CollectionReference {
        val favoriteSongsRef = firestore.collection("users").document(userId.toString()).collection("favorite")
        return favoriteSongsRef
    }

    fun getPlaylistRef(userId: String?, playlistName: String): DocumentReference {
        val playlistRef = firestore.collection("users").document(userId.toString()).collection("albums").document(playlistName)
        return playlistRef
    }

    fun getPlaylistSongRef(userId: String?, playlistName: String, url: String): DocumentReference {
        val playlistSongRef = getPlaylistRef(userId, playlistName).collection("songs").document(url)
        return playlistSongRef
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
            "email" to email,
            "uid" to userId.toString(),
            "friends" to emptyList<String>(),
            "waiting_queue" to emptyList<String>()
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
                val albums = mutableListOf<FirebasePlaylist?>()
                for (document in result) {
                    Log.d("album", "${document.id} => ${document.data}")
                    albums.add(FirebasePlaylist(document.getString("albumName").toString(),
                        document.getLong("songCount")?.toInt() ?: 0
                    ))
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

        getAlbums { state ->
            when (state) {
                is DataFetchingState.Success -> {
                    val albums = state.data as List<String?>
                    if (albums.contains(albumName)) {
                        callback(DataFetchingState.Error("Album already exists"))
                    } else {
                        val albumMap = hashMapOf(
                            "albumName" to albumName,
                            "songCount" to 0
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
                }
                else -> { }
            }
        }
    }

    override fun addFavoriteSong(song: Song, callback: (FavoriteSongFetchingState) -> Unit) {
        val userId = auth.currentUser?.uid

        Log.d("uid", userId.toString())
        Log.d("url", song.url)

        val urls = mutableListOf<String>()

        getFavoriteSongs { state ->
            when (state) {
                is FavoriteSongFetchingState.Success -> {
                    val songs = state.data as List<Song>
                    songs.forEach {
                        urls.add(it.url)
                    }

                    if (urls.contains(song.url)) {
                        callback(FavoriteSongFetchingState.Error("Song already in favorites"))
                    } else {
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
                                callback(FavoriteSongFetchingState.Success("Successfully added favorite song"))
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error writing document", e)
                                callback(FavoriteSongFetchingState.Error("Failed to add favorite song"))
                            }
                    }
                }
                else -> { }
            }

        }

        getFavoriteSongs(callback = {
            when (it) {
                is FavoriteSongFetchingState.Success -> {
                    val songs = it.data as List<Song>
                    songs.forEach {
                        urls.add(it.url)
                    }
                }
                is FavoriteSongFetchingState.Error -> {
                    Log.d("favorite", "Error getting documents: ")
                }
                else -> {}
            }
        })
    }

    override fun removeFavoriteSong(
        song: Song,
        callback: (FavoriteSongFetchingState) -> Unit
    ) {
        val userId = auth.currentUser?.uid
        val encodedUrl = URLEncoder.encode(song.url, StandardCharsets.UTF_8.toString())

        val favoriteSongRef = getFavoriteSongRef(userId, encodedUrl)

        favoriteSongRef.delete()
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully deleted!")
                callback(FavoriteSongFetchingState.Success("Successfully removed favorite song"))
//                getFavoriteSongs(callback)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error deleting document", e)
                callback(FavoriteSongFetchingState.Error("Failed to remove favorite song"))
            }
    }

    override fun getFavoriteSongs(callback: (FavoriteSongFetchingState) -> Unit) {
        val userId = auth.currentUser?.uid
        val favoriteSongsRef = getFavoriteSongsRef(userId)

        favoriteSongsRef.get()
            .addOnSuccessListener { result ->
                val favoriteSongs = mutableListOf<Song>()
                for (document in result) {
                    Log.d("favorite", "${document.id} => ${document.data}")
                    val song = Song(
                        id = document.id,
                        name = document.getString("songName").toString(),
                        artist = document.getString("artist").toString(),
                        imageResId = document.getString("imageResId").toString(),
                        url = document.getString("url").toString()
                    )
                    favoriteSongs.add(song)
                }
                callback(FavoriteSongFetchingState.Success(favoriteSongs))
            }
            .addOnFailureListener { exception ->
                Log.d("favorite", "Error getting documents: ", exception)
                callback(FavoriteSongFetchingState.Error("Failed to get favorite songs"))
            }
    }

    fun updateSongCount(albumName: String, count: Int) {
        val userId = auth.currentUser?.uid
        val albumRef = getPlaylistRef(userId, albumName)

        val albumMap = hashMapOf<String, Any> (
            "songCount" to count
        )

        albumRef.update(albumMap)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating document", e)
            }
    }

    fun getSongCount(albumName: String, callback: (Int) -> Unit) {
        val userId = auth.currentUser?.uid
        val albumRef = getPlaylistRef(userId, albumName)

        albumRef.get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val count = document.getLong("songCount")?.toInt()
                    callback(count ?: 0)
                } else {
                    Log.d("OwO", "No such document")
                    callback(0)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
                callback(0)
            }
    }

    override fun addSongToPlayList(
        song: Song,
        playlistName: String,
        callback: (PlaylistSongFetchingState) -> Unit
    ) {
        val userId = auth.currentUser?.uid
        val encodedUrl = URLEncoder.encode(song.url, StandardCharsets.UTF_8.toString())
        val playlistSongsRef = getPlaylistSongRef(userId, playlistName, encodedUrl)

        val songMap = hashMapOf(
            "songName" to song.name,
            "artist" to song.artist,
            "imageResId" to song.imageResId,
            "url" to song.url
        )

        getPlaylistSongs(playlistName) { state ->
            when (state) {
                is PlaylistSongFetchingState.Success -> {
                    val songs = state.data as List<Song>
                    var songExists = false
                    songs.forEach {
                        if (it.url == song.url) {
                            callback(PlaylistSongFetchingState.Error("Song already in playlist"))
                            songExists = true
                        }
                    }
                    if (!songExists) {
                        playlistSongsRef.set(songMap)
                            .addOnSuccessListener {
                                Log.d(TAG, "DocumentSnapshot successfully written!")
                                callback(PlaylistSongFetchingState.Success("Successfully added song to ${playlistName}"))
                                getSongCount(playlistName) { count ->
                                    updateSongCount(playlistName, count + 1)
                                }
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error writing document", e)
                                callback(PlaylistSongFetchingState.Error("Failed to add song to ${playlistName}"))
                            }
                    }
                }

                else -> {
                    callback(PlaylistSongFetchingState.Error("Failed to fetch playlist songs"))
                }
            }
        }
    }

    override fun getPlaylistSongs(
        playlistName: String,
        callback: (PlaylistSongFetchingState) -> Unit
    ) {
        val userId = auth.currentUser?.uid
        val playlistRef = getPlaylistRef(userId, playlistName)

        playlistRef.collection("songs").get()
            .addOnSuccessListener { result ->
                val playlistSongs = mutableListOf<Song>()
                for (document in result) {
                    Log.d("playlist", "${document.id} => ${document.data}")
                    val song = Song(
                        id = document.id,
                        name = document.getString("songName").toString(),
                        artist = document.getString("artist").toString(),
                        imageResId = document.getString("imageResId").toString(),
                        url = document.getString("url").toString()
                    )
                    playlistSongs.add(song)
                }
                callback(PlaylistSongFetchingState.Success(playlistSongs))
            }
            .addOnFailureListener { exception ->
                Log.d("playlist", "Error getting documents: ", exception)
                callback(PlaylistSongFetchingState.Error("Failed to get playlist songs"))
            }
    }

    override fun removeSongFromPlayList(
        song: Song,
        playlistName: String,
        callback: (PlaylistSongFetchingState) -> Unit
    ) {
        Log.d("RemovePlaylist", "removing song from playlist ${song.name}")
        val userId = auth.currentUser?.uid
        val encodedUrl = URLEncoder.encode(song.url, StandardCharsets.UTF_8.toString())
        val playlistSongRef = getPlaylistSongRef(userId, playlistName, encodedUrl)

        Log.d("RemovePlaylist", "removing song from playlist ${playlistSongRef.id}")

        var flag = true

        playlistSongRef.delete()
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully deleted!")
                flag = false
                callback(PlaylistSongFetchingState.Success("Successfully removed song from ${playlistName}"))
                getSongCount(playlistName) { count ->
                updateSongCount(playlistName, count - 1)
                Log.d("RemovePlaylist", "song count updated to ${count - 1}")
            }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error deleting document", e)
                callback(PlaylistSongFetchingState.Error("Failed to remove song from ${playlistName}"))
            }

    }

    override fun deletePlayList(playlistName: String) {
        val userId = auth.currentUser?.uid
        val playlistRef = getPlaylistRef(userId, playlistName)

        playlistRef.delete()
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully deleted!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error deleting document", e)
            }
    }

    override fun getUsers(callback: (List<FirebaseUser>) -> Unit) {
        val usersRef = firestore.collection("users")

        usersRef.get()
            .addOnSuccessListener { result ->
                val users = mutableListOf<FirebaseUser>()
                for (document in result) {
                    Log.d("user", "${document.id} => ${document.data}")
                    val user = FirebaseUser(
                        email = document.getString("email").toString(),
                        uid = document.getString("uid").toString(),
                        userName = document.getString("userName").toString()
                    )
                    users.add(user)
                }
                callback(users)
            }
            .addOnFailureListener { exception ->
                Log.d("user", "Error getting documents: ", exception)
                callback(emptyList())
            }
    }

    override fun searchForEmail(email: String, callback: (FriendListFetchingState) -> Unit) {
        getUsers {
            val users = it
            val user = users.find { user -> user.email == email }
            if (user != null) {
                callback(FriendListFetchingState.Success("Successfully send friend request to ${user.userName}"))
            } else {
                callback(FriendListFetchingState.Error("User not found"))
            }
        }
    }
}

data class FirebasePlaylist(
    val name: String,
    val songCount: Int
)