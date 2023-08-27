package com.example.myandroidapplication.viewModel

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myandroidapplication.R
import com.example.myandroidapplication.model.Player
import com.example.myandroidapplication.util.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class StatsReceiver : AppCompatActivity(){

    private lateinit var relativeLayout: RelativeLayout
    // Variabile utilizzata per le autenticazioni Firebase
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    private lateinit var userTag: String
    private lateinit var userName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_stats_receiver)

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()

        //carica il navigation drawer
        relativeLayout = findViewById(R.id.relative_layout_stats_receiver)

        //SEZIONE PER LA RECYCLER VIEW
        val recyclerView_main = findViewById<RecyclerView>(R.id.recyclerView_main)
        recyclerView_main.layoutManager = LinearLayoutManager(this)

        userTag = intent.getStringExtra("tag")?: ""
        userName = intent.getStringExtra("name")?: ""

        try {
            getPlayer(userTag)
        } catch (e: Exception) {
            // Eccezione generica per qualsiasi altra eccezione
            val errorMessage = "Si è verificato un errore: ${e.message}"
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    // Funzione per prendere i dati del giocatore registrato
    private fun getPlayer(rawTag: String){
        // costruzione dell'url e della richiesta HTTP
        val currentUser = mAuth.currentUser
        try {
            // Aggiunta di un ValueEventListener per ottenere il tag e l'apiKey dell'utente dal database
            currentUser?.let {
                val uid = it.uid
                mDbRef.child("user").child(uid).get().addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        val apiKey = snapshot.child("apiKey").getValue(String::class.java) ?: ""
                        val tag = rawTag.replace("#", "%23")
                        val correctUrl = Constants.PLAYERS_URL + tag
                        val client = OkHttpClient()
                        val request = Request.Builder()
                            .url(correctUrl)
                            .addHeader("authorization","Bearer $apiKey")
                            .build()
                        client.newCall(request).enqueue(object : okhttp3.Callback{
                            // funzione che si attiva in caso di una risposta
                            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                                runOnUiThread {
                                    val responseBody = response.body?.string()

                                    val gson = GsonBuilder().create()
                                    val giocatore = gson.fromJson(responseBody, Player::class.java)

                                    val recyclerViewMain = findViewById<RecyclerView>(R.id.recyclerView_main)
                                    recyclerViewMain.adapter = StatsReceiverAdapter(giocatore)
                                }
                            }
                            // funzione che si attiva in caso di fallimento
                            override fun onFailure(call: okhttp3.Call, e: IOException) {
                                Log.d("StatsReceiver", "onFailure: "+e.message)
                            }
                        })
                    }
                }.addOnFailureListener { e ->
                    Toast.makeText(
                        this@StatsReceiver,
                        "Error " + "${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(
                this@StatsReceiver,
                "Error " + "${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

