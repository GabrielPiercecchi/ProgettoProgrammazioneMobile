package com.example.myandroidapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.myandroidapplication.R
import com.example.myandroidapplication.util.Constants.Companion.PLAYERS_URL
import com.example.myandroidapplication.util.Constants.Companion.API_KEY_G
import com.example.myandroidapplication.model.Player
import com.example.myandroidapplication.util.Constants.Companion.API_KEY
import com.example.myandroidapplication.util.Constants.Companion.API_KEY_L
import com.example.myandroidapplication.viewModel.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LeaderboardsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboards)

        // Chiamata della funzione
        getPlayer()
    }

    private fun getPlayer(){
        // Creo il builder di retrofit con l'URL desiderato
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(PLAYERS_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)


        // Eseguo il metodo getPlayer() dell' ApiInterface
        val retrofitData = retrofitBuilder.getPlayer("Bearer $API_KEY", "#2RYPJYY")
//        val retrofitData = retrofitBuilder.getLeaderboard()


        // Costruzione della risposta
        retrofitData.enqueue(object : Callback<List<Player>?> {
            override fun onResponse(call: Call<List<Player>?>, response: Response<List<Player>?>) {
                val responseBody = response.body()!!


                // Costruzione della string di risposta e applicazione della risposta alla vista
                val myStringBuilder = StringBuilder()
                for(myData in responseBody){
                    myStringBuilder.append(myData.name)
                    myStringBuilder.append("\n")
                }
                val txtId: TextView = findViewById(R.id.txtId)
                txtId.text = myStringBuilder
            }

            // Se fallisce...
            override fun onFailure(call: Call<List<Player>?>, t: Throwable) {
                Log.d("MainActivity", "onFailure: "+t.message)
            }
        })
    }
}