package com.example.myandroidapplication.ViewModel

import com.example.myandroidapplication.model.Player
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("player")
    fun getData(): Call<List<Player>>

}