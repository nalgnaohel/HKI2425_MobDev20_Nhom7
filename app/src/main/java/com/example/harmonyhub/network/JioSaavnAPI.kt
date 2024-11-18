package com.example.harmonyhub.network

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

// Các lớp dữ liệu tương ứng với cấu trúc JSON
data class ApiResponse(
    val success: Boolean,
    val data: Data
)

data class Data(
    val albums: Albums,
    val songs: Songs,
    val artists: Artists,
    val playlists: Playlists,
    val topQuery: TopQuery
)

data class Albums(
    val results: List<AlbumResult>,
    val position: Int
)

data class AlbumResult(
    val id: String,
    val title: String,
    val image: List<Image>,
    val artist: String,
    val url: String,
    val type: String,
    val description: String,
    val year: String,
    val language: String,
    val songIds: String
)

data class Songs(
    val results: List<SongResult>,
    val position: Int
)

data class SongResult(
    val id: String,
    val title: String,
    val image: List<Image>,
    val album: String,
    val url: String,
    val type: String,
    val description: String,
    val primaryArtists: String,
    val singers: String,
    val language: String
)

data class Artists(
    val results: List<ArtistResult>,
    val position: Int
)

data class ArtistResult(
    val id: String,
    val title: String,
    val image: List<Image>,
    val type: String,
    val description: String,
    val position: Int
)

data class Playlists(
    val results: List<PlaylistResult>,
    val position: Int
)

data class PlaylistResult(
    val id: String,
    val title: String,
    val image: List<Image>,
    val url: String,
    val language: String,
    val type: String,
    val description: String
)

data class TopQuery(
    val results: List<TopQueryResult>,
    val position: Int
)

data class TopQueryResult(
    val id: String,
    val title: String,
    val image: List<Image>,
    val album: String,
    val url: String,
    val type: String,
    val description: String,
    val primaryArtists: String,
    val singers: String,
    val language: String
)

data class Image(
    val quality: String,
    val url: String
)


fun searchGlobal(keyWord: String) {
    val client = OkHttpClient()

    val request = Request.Builder()
        .url("https://saavn.dev/api/search?query=" + keyWord)
        .get()
        .build()

    val response = client.newCall(request).execute()

    // Kiểm tra xem phản hồi có thành công không
    if (response.isSuccessful) {
        val jsonResponse = response.body?.string()
        //println("Response: $jsonResponse")

        // Kiểm tra nếu jsonResponse không null và là chuỗi hợp lệ
        if (jsonResponse != null) {
            try {
                // Sử dụng Gson để chuyển chuỗi JSON thành đối tượng
                val gson = Gson()
                val responseData = gson.fromJson(jsonResponse, ApiResponse::class.java)

                // Trích xuất dữ liệu
                if (responseData.success) {
                    //trich xuat
                    val firstResult = responseData.data.topQuery.results.firstOrNull()
                    if (firstResult != null) {

                        println("First Result Title: ${firstResult.title}")
                        println("First Result URL: ${firstResult.url}")
                        for (image in firstResult.image) {
                            println("First Result Image: ${image.url}")
                        }
                    }
                } else {
                    println("Error: Request was not successful")
                }
            } catch (e: Exception) {
                println("Error parsing JSON: ${e.message}")
            }
        } else {
            println("Error: JSON response is null.")
        }
    } else {
        println("Request failed with code: ${response.code}")
    }
}

fun main() {
    searchGlobal("Shape Of You Alan Walker")
}