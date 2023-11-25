package com.smartplanwatch.app.network

import com.example.smartplanwatch.presentation.model.WatchRes
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("watch.php")
    fun getWatchData(): Call<WatchRes>
}