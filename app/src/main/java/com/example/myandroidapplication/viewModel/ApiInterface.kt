package com.example.myandroidapplication.viewModel

import com.example.myandroidapplication.Util.Constants.Companion.API_KEY
import com.example.myandroidapplication.model.Player
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("players/{PlayerTAG}/Bearer $API_KEY")
    fun getPlayer(@Path("%232RYPJYY") PlayerTAG: String, @Query("authorization") query: String): Call<List<Player>>
    @GET("locations")
    fun getLeaderboard(): Call<List<Player>>

}