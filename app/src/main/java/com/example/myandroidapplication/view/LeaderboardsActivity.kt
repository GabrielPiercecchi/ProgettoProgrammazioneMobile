package com.example.myandroidapplication.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.myandroidapplication.R
import com.example.myandroidapplication.model.Location
import com.example.myandroidapplication.model.Locations
import com.example.myandroidapplication.util.Constants
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class LeaderboardsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboards)

        getAllLocations()
        getPlayerLeaderboardForLocation()

    }

    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private lateinit var adapterItems: ArrayAdapter<String>

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
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                runOnUiThread {
                    val responseBody = response.body?.string()

                    val gson = GsonBuilder().create()
                    val locations = gson.fromJson(responseBody, Locations::class.java)

                    /* TODO: fare in modo che locations sia stringa, ArrayAdapter accetta solo stringhe
                        in quanto lo vuole proprio ArrayAdapter
                     */
                    adapterItems =  ArrayAdapter(this, R.layout.list_item, locations)

                    autoCompleteTextView.setAdapter(adapterItems)
                    autoCompleteTextView.setOnItemClickListener { adapterView, view, position, l ->
                        val item = adapterView.getItemAtPosition(position).toString()
                        Toast.makeText(this@LeaderboardsActivity, item, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            // funzione che si attiva in caso di fallimento
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Log.d("MainActivity", "onFailure: "+e.message)
            }
        })
    }
    // Tira fuori la leaderboard dei giocatori della location selezionata
    fun getPlayerLeaderboardForLocation(){
        // TODO: Implementare questa funzione per bene.
    }
}