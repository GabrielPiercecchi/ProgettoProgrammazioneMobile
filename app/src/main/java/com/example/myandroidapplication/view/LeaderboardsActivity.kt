package com.example.myandroidapplication.view

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myandroidapplication.R
import com.example.myandroidapplication.model.Locations
import com.example.myandroidapplication.model.Players
import com.example.myandroidapplication.util.Constants
import com.google.gson.GsonBuilder
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


class LeaderboardsActivity : AppCompatActivity() {

    var itemFromSpinner: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboards)

        //SEZIONE PER LA RECYCLER VIEW
        val rv_leaderboards = findViewById<RecyclerView>(R.id.rv_leaderboards)
        rv_leaderboards.layoutManager = LinearLayoutManager(this)

        getAllLocations()

        val rg: RadioGroup = findViewById(R.id.rg)

        val bUpdate: Button = findViewById(R.id.b_update)
        /**
         * TODO: capire come mai itemFromSpinner risulta sempre stringa vuota
         */
        bUpdate.setOnClickListener{

            when(rg.checkedRadioButtonId) {
                R.id.rb_player -> getPlayersNormalLeaderboardForLocation(itemFromSpinner)
                R.id.rb_builder -> getPlayersBuilderLeaderboardForLocation(itemFromSpinner)
                R.id.rb_clan -> getClansNormalLeaderboardForLocation(itemFromSpinner)
            }
        }
    }


    private lateinit var adapterItems: ArrayAdapter<String>

    // Funzione che ti permette di prendere tutte le locations e metterle nella select list.
    private fun getAllLocations(){
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
                    val location = gson.fromJson(responseBody, Locations::class.java)

                    val dataMap: List<String> = location.items.take(location.items.size).map { it.id.toString() }

                    val spinner: Spinner = findViewById(R.id.spinner)

                    try {
                        adapterItems = ArrayAdapter(this@LeaderboardsActivity, android.R.layout.simple_spinner_dropdown_item, dataMap)
                        spinner.adapter = adapterItems

                        spinner.setOnItemClickListener { adapterView, _, position, _ ->
                            itemFromSpinner = adapterView.getItemAtPosition(position).toString()
                            Toast.makeText(this@LeaderboardsActivity, itemFromSpinner, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception){
                        Log.d("ciao", "ciao di nuovo")
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
    fun getPlayersNormalLeaderboardForLocation(locationid: String) {
        // costruzione dell'url e della richiesta HTTP
        Constants.LOCATION_ID = locationid
        val client = OkHttpClient()
        val request = Request.Builder()
            //.url(Constants.RANKING_PLAYERS_NORMAL)
            .url("https://api.clashofclans.com/v1/locations/$locationid/rankings/players")
            .addHeader("authorization", "Bearer ${Constants.API_KEY}")
            .build()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            // funzione che si attiva in caso di una risposta
            override fun onResponse(call: Call, response: okhttp3.Response) {
                runOnUiThread {
                    val responseBody = response.body?.string()

                    val gson = GsonBuilder().create()
                    val playersForLocation = gson.fromJson(responseBody, Players::class.java)

                    /**
                     * TODO: capire come mai qui passa una lista di null e non la lista con i players
                     */
                    val recyclerViewLeaderboards = findViewById<RecyclerView>(R.id.rv_leaderboards)
                    recyclerViewLeaderboards.adapter = LeaderboardsAdapter(playersForLocation)
                }
            }

            // Funzione che si attiva in caso di fallimento
            override fun onFailure(call: Call, e: IOException) {
                Log.d("MainActivity", "onFailure: "+e.message)
            }
        })
    }

    fun getPlayersBuilderLeaderboardForLocation(locationid: String) {
        // costruzione dell'url e della richiesta HTTP
        Constants.LOCATION_ID = locationid
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

                    val recyclerViewLeaderboards = findViewById<RecyclerView>(R.id.rv_leaderboards)
                    recyclerViewLeaderboards.adapter = LeaderboardsAdapter(playersForLocation)
                }
            }

            // Funzione che si attiva in caso di fallimento
            override fun onFailure(call: Call, e: IOException) {
                Log.d("MainActivity", "onFailure: "+e.message)
            }
        })
    }

    fun getClansNormalLeaderboardForLocation(locationid: String) {
        // costruzione dell'url e della richiesta HTTP
        Constants.LOCATION_ID = locationid
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

                    val recyclerViewLeaderboards = findViewById<RecyclerView>(R.id.rv_leaderboards)
                    recyclerViewLeaderboards.adapter = LeaderboardsAdapter(playersForLocation)
                }
            }

            // Funzione che si attiva in caso di fallimento
            override fun onFailure(call: Call, e: IOException) {
                Log.d("MainActivity", "onFailure: "+e.message)
            }
        })
    }

    fun getClansBuilderLeaderboardForLocation(locationid: String) {
        // costruzione dell'url e della richiesta HTTP
        Constants.LOCATION_ID = locationid
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

                    val recyclerViewLeaderboards = findViewById<RecyclerView>(R.id.rv_leaderboards)
                    recyclerViewLeaderboards.adapter = LeaderboardsAdapter(playersForLocation)
                }
            }

            // Funzione che si attiva in caso di fallimento
            override fun onFailure(call: Call, e: IOException) {
                Log.d("MainActivity", "onFailure: "+e.message)
            }
        })
    }

    fun getClansCapitalLeaderboardForLocation(locationid: String) {
        // costruzione dell'url e della richiesta HTTP
        Constants.LOCATION_ID = locationid
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

                    val recyclerViewLeaderboards = findViewById<RecyclerView>(R.id.rv_leaderboards)
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