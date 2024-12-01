package com.example.harmonyhub.data.repository


import com.example.harmonyhub.data.network.AlbumOut
import com.example.harmonyhub.data.network.ArtistOut
import com.example.harmonyhub.data.network.ChartOut

import com.example.harmonyhub.data.network.Response
import com.example.harmonyhub.data.network.ResponseHomeScreenData
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

class DefaultHomeScreenRepo : HomeScreenRepo {
    override suspend fun updatePopularItem(): ResponseHomeScreenData? {
        val listPopularArtist : MutableList<ArtistOut>? = mutableListOf()
        val listPopularAlbums: MutableList<AlbumOut>? = mutableListOf()
        val listChart: MutableList<ChartOut>? = mutableListOf()

        var result : ResponseHomeScreenData? = null

        try {
            val client = OkHttpClient()

            val request = Request.Builder()
                .url("https://spotify-scraper.p.rapidapi.com/v1/home")
                .get()
                .addHeader("x-rapidapi-key", "89644839e0mshcae86286ffb46fcp1e2f10jsn5dacb407fc3b")
                .addHeader("x-rapidapi-host", "spotify-scraper.p.rapidapi.com")
                .build()

            val response = client.newCall(request).execute()

            // Kiểm tra xem phản hồi có thành công không
            if (response.isSuccessful) {
                val jsonResponse = response.body?.string()
                //println("Response: $jsonResponse")
                // Kiểm tra nếu jsonResponse không null và là chuỗi hợp lệ
                if (jsonResponse != null  && jsonResponse.isNotEmpty()) {
                    try {

                        val gson = Gson()
                        val responseData = gson.fromJson(jsonResponse, Response::class.java)

                        // Trích xuất dữ liệu
                        if (responseData.status) {
                            //trich xuat Popular Artise
                            val PopularArtist = responseData.sections.items[0].contents.items
                            val PopularAlbums = responseData.sections.items[1].contents.items
                            val PopularChart = responseData.sections.items[3].contents.items
                            listPopularArtist?.addAll(PopularArtist.map {
                                ArtistOut(it.id, it.name, it.visuals?.avatar?.get(1)?.url)
                            })
                            listPopularAlbums?.addAll(PopularAlbums.map {
                                AlbumOut(it.id, it.name, it.cover[1].url, it.artists.map { artist -> artist.name })
                            })
                            listChart?.addAll(PopularChart.map {
                                ChartOut(it.name, it.images?.get(0)?.get(0)?.url, it.id)
                            })
//                            for (i in PopularArtist!!) {
//                                val subArtist = ArtistOut(i.id, i.name, i.visuals?.avatar?.get(1)?.url)
//                                listPopularArtist?.add(subArtist)
//                            }
//
//                            for (i in PopularAlbums!!) {//duyet phan tu album
//                                var listArtistInAlbum : MutableList<String> = mutableListOf()
//                                for( j in i.artists) {// truy van nghe si cua album
//                                    listArtistInAlbum.add(j.name)
//                                }
//                                val subAlbum = AlbumOut(i.id, i.name, i.cover[1].url, listArtistInAlbum)
//                                listPopularAlbums?.add(subAlbum)
//                            }
//                            for (i in PopularChart!!) {
//                                val subChart = ChartOut(i.name, i.images?.get(0)?.get(0)?.url, i.id)
//                                listChart?.add(subChart)
//                            }

                            result = ResponseHomeScreenData(listPopularArtist, listPopularAlbums, listChart)
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
