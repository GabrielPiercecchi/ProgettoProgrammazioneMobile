package com.example.myandroidapplication.viewModel

import com.example.myandroidapplication.model.Player
import com.example.myandroidapplication.util.*
import com.example.myandroidapplication.util.Constants.Companion.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiInterface {

    @GET("locations")
    fun getLeaderboard(): Call<List<Player>>
    @GET("players/{PlayerTAG}")
    fun getPlayer(s: String): Call<Player>


}