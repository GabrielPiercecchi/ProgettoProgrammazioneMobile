package com.example.myandroidapplication.viewModel

import com.example.myandroidapplication.model.Player
import com.example.myandroidapplication.util.*
import com.example.myandroidapplication.util.Constants.Companion.API_KEY
import com.example.myandroidapplication.util.Constants.Companion.API_KEY_L
import com.example.myandroidapplication.util.Constants.Companion.API_KEY_Z
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
interface ApiInterface {

    /*@GET("players/{PlayerTAG}")
    @Headers("authorization: Bearer $API_KEY", "Accept: application/json")
    fun getPlayer(@Path("PlayerTAG")PlayerTAG: String): Call<Player>
    //fun getPlayer(@Path("PlayerTAG")PlayerTAG: String, @Query("authorization") token: String): Call<List<Player>>*/
    @GET("locations")
    fun getLeaderboard(): Call<List<Player>>
    @GET("players/{PlayerTAG}")
    fun getPlayer(): Call<Player>


}