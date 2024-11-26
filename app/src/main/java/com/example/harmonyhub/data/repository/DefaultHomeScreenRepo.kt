package com.example.harmonyhub.data.repository


import com.example.harmonyhub.data.network.AlbumOut
import com.example.harmonyhub.data.network.ArtistOut

import com.example.harmonyhub.data.network.Response
import com.example.harmonyhub.data.network.ResponseHomeScreenData
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

class DefaultHomeScreenRepo : HomeScreenRepo {
    override suspend fun updatePopularItem(): ResponseHomeScreenData? {
        val listPopularArtist : MutableList<ArtistOut>? = mutableListOf()

        val listPopularAlbums: MutableList<AlbumOut>? = mutableListOf()

        var result : ResponseHomeScreenData? = null
        try {

            val client = OkHttpClient()

            val request = Request.Builder()
                .url("https://spotify-scraper.p.rapidapi.com/v1/home")
                .get()
                .addHeader("x-rapidapi-key", "c18da195b0mshcdebcf46df53015p1a1b64jsn33955d1b96fc")
                .addHeader("x-rapidapi-host", "spotify-scraper.p.rapidapi.com")
                .build()

            val response = client.newCall(request).execute()
            // Kiểm tra xem phản hồi có thành công không
            if (response.isSuccessful) {
                val jsonResponse = response.body?.string()
                //println("Response: $jsonResponse")

                // Kiểm tra nếu jsonResponse không null và là chuỗi hợp lệ
                if (jsonResponse != null) {
                    try {
                        val gson = Gson()
                        val responseData = gson.fromJson(jsonResponse, Response::class.java)

                        // Trích xuất dữ liệu
                        if (responseData.status) {
                            //trich xuat Popular Artise
                            val PopularArtist = responseData.sections.items[0].contents.items
                            val PopularAlbums = responseData.sections.items[1].contents.items
                            for (i in PopularArtist!!) {
                                val subArtist =
                                    ArtistOut(i.id, i.name, i.visuals?.avatar?.get(1)?.url)
                                listPopularArtist?.add(subArtist)
                            }

                            for (i in PopularAlbums!!) {//duyet phan tu album
                                var listArtistInAlbum: MutableList<String> = mutableListOf()
                                for (j in i.artists) {// truy van nghe si cua album
                                    listArtistInAlbum.add(j.name)
                                }
                                val subAlbum =
                                    AlbumOut(i.id, i.name, i.cover[1].url, listArtistInAlbum)
                                listPopularAlbums?.add(subAlbum)
                            }
                            result = ResponseHomeScreenData(listPopularArtist, listPopularAlbums)
                            return result
//                        for (i in listPopularArtist!!) {
//                            println("${i.id} - ${i.name} - ${i.image}")
//                        }
//                        for (i in listPopularAlbums!!) {
//                            println("${i.id} - ${i.name} - ${i.image} ")
//                            for( j in i.listArtist) {
//                                println("${j}")
//                            }
//                        }

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
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            return result
        }

    }
}
