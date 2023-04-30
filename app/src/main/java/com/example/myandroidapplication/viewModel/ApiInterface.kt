package com.example.myandroidapplication.viewModel

import com.example.myandroidapplication.model.Player
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("player")
    fun getPlayer(): Call<List<Player>>

    @GET("locations")
    fun getLeaderboard(): Call<List<Player>>

}