package com.example.myandroidapplication.view

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myandroidapplication.R
import com.example.myandroidapplication.util.Constants.Companion.PLAYERS_URL
import java.io.IOException
import com.example.myandroidapplication.model.Player
import com.example.myandroidapplication.util.Constants.Companion.API_KEY
import com.example.myandroidapplication.viewModel.ApiInterface
import okhttp3.OkHttpClient
import okhttp3.Request

class LeaderboardsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboards)

    }
}