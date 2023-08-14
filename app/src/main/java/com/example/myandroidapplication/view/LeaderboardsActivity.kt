package com.example.myandroidapplication.view

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myandroidapplication.R
import com.example.myandroidapplication.model.Location
import com.example.myandroidapplication.model.Locations
import com.example.myandroidapplication.model.Players
import com.example.myandroidapplication.util.Constants
import com.google.gson.GsonBuilder
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class LeaderboardsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboards)

        getAllLocations()

        //SEZIONE PER LA RECYCLER VIEW
        val recyclerView_leaderboards = findViewById<RecyclerView>(R.id.recyclerView_leaderboards)
        recyclerView_leaderboards.layoutManager = LinearLayoutManager(this)

    }

    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private lateinit var adapterItems: ArrayAdapter<Location>

    // Funzione che ti permette di prendere tutte le locations e metterle nella select list.
    fun getAllLocations(){
        // costruzione dell'url e della richiesta HTTP
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Constants.GET_LOCATIONS)
            .addHeader("authorization","Bearer ${Constants.API_KEY}")
            .build()
        client.newCall(request).enqueue(object : okhttp3.Callback{
            // funzione che si attiva in caso di una risposta
            override fun onResponse(call: Call, response: okhttp3.Response) {
                runOnUiThread {
                    val responseBody = response.body?.string()

                    val gson = GsonBuilder().create()
                    val locations = gson.fromJson(responseBody, Locations::class.java)

                    /**
                     * TODO: vedere come cristo sistemare questo ArrayAdapter
                     * */

                    adapterItems =  ArrayAdapter(this@LeaderboardsActivity, R.layout.list_item, locations)

                    autoCompleteTextView.setAdapter(adapterItems)
                    autoCompleteTextView.setOnItemClickListener { adapterView, view, position, l ->
                        val item = adapterView.getItemAtPosition(position).toString()
                        Toast.makeText(this@LeaderboardsActivity, item, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            // funzione che si attiva in caso di fallimento
            override fun onFailure(call: Call, e: IOException) {
                Log.d("MainActivity", "onFailure: "+e.message)
            }
        })
    }

    /**
     * TODO: implementare correttamente queste funzioni
     * */

    // Tira fuori la leaderboard dei giocatori della location selezionata
    fun getPlayersNormalLeaderboardForLocation(/*aggiungere il location id corrispondente*/) {
        // costruzione dell'url e della richiesta HTTP
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Constants.RANKING_PLAYERS_NORMAL)
            .addHeader("authorization", "Bearer ${Constants.API_KEY}")
            .build()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            // funzione che si attiva in caso di una risposta
            override fun onResponse(call: Call, response: okhttp3.Response) {
                runOnUiThread {
                    val responseBody = response.body?.string()

                    val gson = GsonBuilder().create()
                    val playersForLocation = gson.fromJson(responseBody, Players::class.java)

                    val recyclerViewLeaderboards = findViewById<RecyclerView>(R.id.recyclerView_leaderboards)
                    recyclerViewLeaderboards.adapter = LeaderboardsAdapter(playersForLocation)
                }
            }

            // Funzione che si attiva in caso di fallimento
            override fun onFailure(call: Call, e: IOException) {
                Log.d("MainActivity", "onFailure: "+e.message)
            }
        })
    }

    fun getPlayersBuilderLeaderboardForLocation(/*aggiungere il location id corrispondente*/) {
        // costruzione dell'url e della richiesta HTTP
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Constants.RANKING_PLAYERS_BUILDER)
            .addHeader("authorization", "Bearer ${Constants.API_KEY}")
            .build()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            // funzione che si attiva in caso di una risposta
            override fun onResponse(call: Call, response: okhttp3.Response) {
                runOnUiThread {
                    val responseBody = response.body?.string()

                    val gson = GsonBuilder().create()
                    val playersForLocation = gson.fromJson(responseBody, Players::class.java)

                    val recyclerViewLeaderboards = findViewById<RecyclerView>(R.id.recyclerView_leaderboards)
                    recyclerViewLeaderboards.adapter = LeaderboardsAdapter(playersForLocation)
                }
            }

            // Funzione che si attiva in caso di fallimento
            override fun onFailure(call: Call, e: IOException) {
                Log.d("MainActivity", "onFailure: "+e.message)
            }
        })
    }

    fun getClansNormalLeaderboardForLocation(/*aggiungere il location id corrispondente*/) {
        // costruzione dell'url e della richiesta HTTP
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Constants.RANKING_CLANS_NORMAL)
            .addHeader("authorization", "Bearer ${Constants.API_KEY}")
            .build()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            // funzione che si attiva in caso di una risposta
            override fun onResponse(call: Call, response: okhttp3.Response) {
                runOnUiThread {
                    val responseBody = response.body?.string()

                    val gson = GsonBuilder().create()
                    val playersForLocation = gson.fromJson(responseBody, Players::class.java)

                    val recyclerViewLeaderboards = findViewById<RecyclerView>(R.id.recyclerView_leaderboards)
                    recyclerViewLeaderboards.adapter = LeaderboardsAdapter(playersForLocation)
                }
            }

            // Funzione che si attiva in caso di fallimento
            override fun onFailure(call: Call, e: IOException) {
                Log.d("MainActivity", "onFailure: "+e.message)
            }
        })
    }

    fun getClansBuilderLeaderboardForLocation(/*aggiungere il location id corrispondente*/) {
        // costruzione dell'url e della richiesta HTTP
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Constants.RANKING_CLANS_BUILDER)
            .addHeader("authorization", "Bearer ${Constants.API_KEY}")
            .build()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            // funzione che si attiva in caso di una risposta
            override fun onResponse(call: Call, response: okhttp3.Response) {
                runOnUiThread {
                    val responseBody = response.body?.string()

                    val gson = GsonBuilder().create()
                    val playersForLocation = gson.fromJson(responseBody, Players::class.java)

                    val recyclerViewLeaderboards = findViewById<RecyclerView>(R.id.recyclerView_leaderboards)
                    recyclerViewLeaderboards.adapter = LeaderboardsAdapter(playersForLocation)
                }
            }

            // Funzione che si attiva in caso di fallimento
            override fun onFailure(call: Call, e: IOException) {
                Log.d("MainActivity", "onFailure: "+e.message)
            }
        })
    }

    fun getClansCapitalLeaderboardForLocation(/*aggiungere il location id corrispondente*/) {
        // costruzione dell'url e della richiesta HTTP
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Constants.RANKING_CLANS_CAPITAL)
            .addHeader("authorization", "Bearer ${Constants.API_KEY}")
            .build()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            // funzione che si attiva in caso di una risposta
            override fun onResponse(call: Call, response: okhttp3.Response) {
                runOnUiThread {
                    val responseBody = response.body?.string()

                    val gson = GsonBuilder().create()
                    val playersForLocation = gson.fromJson(responseBody, Players::class.java)

                    val recyclerViewLeaderboards = findViewById<RecyclerView>(R.id.recyclerView_leaderboards)
                    recyclerViewLeaderboards.adapter = LeaderboardsAdapter(playersForLocation)
                }
            }

            // Funzione che si attiva in caso di fallimento
            override fun onFailure(call: Call, e: IOException) {
                Log.d("MainActivity", "onFailure: "+e.message)
            }
        })
    }
}