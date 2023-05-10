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
import com.example.myandroidapplication.util.Constants.Companion.API_KEY_J
import com.example.myandroidapplication.util.Constants.Companion.API_KEY_L
import com.example.myandroidapplication.util.Constants.Companion.API_KEY_Q
import com.example.myandroidapplication.viewModel.ApiInterface
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class LeaderboardsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboards)

        // Chiamata della funzione
        getPlayer()
    }

     /*private fun getPlayer(){
        // Creo il builder di retrofit con l'URL desiderato
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(PLAYERS_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)


        // Eseguo il metodo getPlayer() dell' ApiInterface
        val retrofitData = retrofitBuilder.getPlayer("#2RYPJYY")
//        val retrofitData = retrofitBuilder.getLeaderboard()


        // Costruzione della risposta
        retrofitData.enqueue(object : Callback<Player> {
            override fun onResponse(call: Call<Player>, response: Response<Player>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!

                    // Costruzione della string di risposta e applicazione della risposta alla vista
                    /*val myStringBuilder = StringBuilder()
                    for (myData in responseBody) {
                        myStringBuilder.append(myData.name)
                        myStringBuilder.append("\n")
                    }*/
                    val txtId: TextView = findViewById(R.id.txtId)
                    txtId.text = "ma salve"
                } else {
                    val txtId: TextView = findViewById(R.id.txtId)
                    txtId.text = "diocane"
                }
            }

            // Se fallisce...
            override fun onFailure(call: Call<Player>, t: Throwable) {
                Log.d("MainActivity", "onFailure: "+t.message)
            }
        })
    }*/

    private fun getPlayer(){
        val client = OkHttpClient()
        val url = PLAYERS_URL
        val request = Request.Builder()
                            .url(url)
                            .addHeader("authorization","Bearer $API_KEY_J")
                            .build()
        client.newCall(request).enqueue(object : okhttp3.Callback{
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if(response.isSuccessful) {
                    runOnUiThread() {
                        val txtId: TextView = findViewById(R.id.txtId)
                        txtId.text = "ma salve"
                    }
                } else {
                    runOnUiThread() {
                        val txtId: TextView = findViewById(R.id.txtId)
                        txtId.text = "Diocane"
                    }
                }
            }

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Log.d("MainActivity", "onFailure: "+e.message)
            }
        })

    }

}