package com.example.harmonyhub.data.repository


import android.util.Log
import com.example.harmonyhub.data.network.AlbumOut
import com.example.harmonyhub.data.network.ArtistOut
import com.example.harmonyhub.data.network.ChartOut

import com.example.harmonyhub.data.network.Response
import com.example.harmonyhub.data.network.ResponseHomeScreenData
import com.example.harmonyhub.data.network.ResponseSplit
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File
import java.util.concurrent.TimeUnit

class DefaultHomeScreenRepo : HomeScreenRepo {
    override suspend fun splitMusic(filePath: String): ResponseSplit? {
        var result : ResponseSplit? = null
//Cấu hình client tăng thời gian chờ
        val client = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.MINUTES) // Thời gian chờ kết nối
            .readTimeout(5, TimeUnit.MINUTES)    // Thời gian chờ đọc dữ liệu
            .writeTimeout(5, TimeUnit.MINUTES)   // Thời gian chờ ghi dữ liệu
            .build()
        val file = File(filePath)
        if (!file.exists()) {
            throw IllegalArgumentException("File không tồn tại tại đường dẫn: ${file.absolutePath}")
        }
        val mediaType =
            "multipart/form-data; boundary=---011000010111000001101001".toMediaTypeOrNull()
        val fileRequestBody = RequestBody.create(mediaType, file)

// Tạo MultipartBody
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM) // multipart/form-data
            .addFormDataPart("input_audio", file.name, fileRequestBody) // Tệp âm thanh
            //.addFormDataPart("type", "RAPID") // Tham số 'type'
            //.addFormDataPart("response_format", "JSON") // Tham số 'response_format'
            .build()

// Tạo Request
        val request = Request.Builder()
            .url("https://splitbeat-vocal-remover-music-splitter.p.rapidapi.com/Upload_audio")
            .post(requestBody)
            .addHeader("x-rapidapi-key", "eeb6435659mshc336b23616278efp1f1318jsn9ff087024dd7")
            .addHeader("x-rapidapi-host", "splitbeat-vocal-remover-music-splitter.p.rapidapi.com")
            .addHeader("Content-Type", "multipart/form-data; boundary=---011000010111000001101001")
            .build()

        try {
            val response: okhttp3.Response = client.newCall(request).execute()
            Log.d("splitapi", "${response}")

            if (response.isSuccessful) {
                // Đọc body response
                val responseBody = response.body?.string()
                Log.d("splitapi", "${responseBody}")

                // Chuyển đổi body thành JSONObject để truy xuất các giá trị
                val jsonResponse = JSONObject(responseBody!!)
                Log.d("splitapi", "${jsonResponse}")
                // Lấy giá trị từ các trường trong JSON
                val bass = jsonResponse.getString("bass_url")
                val drum = jsonResponse.getString("drums_url")
                val beat = jsonResponse.getString("other_url")
                val vocal = jsonResponse.getString("vocals_url")
                result = ResponseSplit(bass, drum, beat, vocal)
                return result
                // Kiểm tra và xử lý giá trị response
            } else {
                println("Response failed with code: ${response.code}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Log.d("Spliterror","loi api")
        return result
    }
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
                .addHeader("x-rapidapi-key", "1ec5c83b55mshd15a7035beade6fp12f050jsn8791b1a67393")
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
