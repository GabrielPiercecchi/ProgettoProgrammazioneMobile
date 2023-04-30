package com.example.myandroidapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.myandroidapplication.R
import com.example.myandroidapplication.Util.Constants.Companion.BASE_URL
import com.example.myandroidapplication.model.Player
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

        getPlayerData()
    }

    private fun getPlayerData(){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getPlayer()

        retrofitData.enqueue(object : Callback<List<Player>?> {
            override fun onResponse(call: Call<List<Player>?>, response: Response<List<Player>?>) {
                val responseBody = response.body()!!

                val myStringBuilder = StringBuilder()
                for(myData in responseBody){
                    myStringBuilder.append(myData.name)
                    myStringBuilder.append("\n")
                }
                val txtId: TextView = findViewById(R.id.txtId)
                txtId.text = myStringBuilder
            }

            override fun onFailure(call: Call<List<Player>?>, t: Throwable) {
                Log.d("MainActivity", "onFailure: "+t.message)
            }
        })
    }
}