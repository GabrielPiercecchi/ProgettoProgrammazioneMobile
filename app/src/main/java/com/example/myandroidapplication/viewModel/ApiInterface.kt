package com.example.myandroidapplication.viewModel

import com.example.myandroidapplication.util.Constants.Companion.API_KEY
import com.example.myandroidapplication.model.Player
import com.example.myandroidapplication.util.Constants.Companion.API_KEY_G
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("players/{PlayerTAG}")
    fun getPlayer(@Path("PlayerTAG")PlayerTAG: String,
                  @Header("authorization") authHeader: String): Call<List<Player>>
    @GET("locations")
    fun getLeaderboard(): Call<List<Player>>

}