package com.example.myandroidapplication.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
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
import com.example.myandroidapplication.viewModel.MainAdapter
import com.example.myandroidapplication.viewModel.util.Constants
import com.example.myandroidapplication.viewModel.util.NetworkUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var bottomNavigationView: BottomNavigationView

    // Variabile utilizzata per le autenticazioni Firebase
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (!NetworkUtils.isInternetAvailable(this)) {
            NetworkUtils.showNoInternetDialog(this)
        }

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()


        //carica il navigation drawer
        drawerLayout = findViewById(R.id.drawer_layout)

        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        //SEZIONE PER LA RECYCLER VIEW
        val recyclerView_main = findViewById<RecyclerView>(R.id.recyclerView_main)
        recyclerView_main.layoutManager = LinearLayoutManager(this)

        try {
            getPlayer()
        } catch (e: Exception) {
            // Eccezione generica per qualsiasi altra eccezione
            val errorMessage = "An Error Has Occurred: ${e.message}"
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }

        // FINE SEZIONE RECYCLER VIEW

        //carica la bottom navigation bar
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        // "when" case per le differenti opzioni della bottom navigation bar
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_playersandchats -> {
                    startActivity(Intent(applicationContext, ChatList::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    true
                }
                R.id.bottom_leaderboards -> {
                    startActivity(Intent(applicationContext, LeaderboardsActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    true
                }
                else -> false
            }
        }
    }

    // Selezione per il menu ad hamburger
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // "when" case per selezionare le differenti opzioni del menu
        when(item.itemId){
            R.id.nav_about -> {
                startActivity(Intent(applicationContext, AboutActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                return true
            }
            R.id.nav_tutorial -> {
                startActivity(Intent(applicationContext, TutorialActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                return true
            }
            R.id.nav_apikey -> {
                startActivity(Intent(applicationContext, ManualApiKeyActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                return true
            }
            R.id.nav_tag -> {
                startActivity(Intent(applicationContext, ChangeTag::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                return true
            }
            R.id.nav_logout -> {
                //Logica per il logout
                mAuth.signOut()
                val intent = Intent(this@MainActivity,
                    Login::class.java)
                finish()
                startActivity(intent)
                return true
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    //funzione per nascondere il menù laterale quando viene premuto il tasto back
    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    // Funzione per prendere i dati del giocatore registrato
    fun getPlayer(){
        // costruzione dell'url e della richiesta HTTP
        val currentUser = mAuth.currentUser
        try {
            // Aggiunta di un ValueEventListener per ottenere il tag e l'apiKey dell'utente dal database
            currentUser?.let {
                val uid = it.uid
                mDbRef.child("user").child(uid).get().addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        val rawTag = snapshot.child("tag").getValue(String::class.java) ?: ""
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
                                    recyclerViewMain.adapter = MainAdapter(giocatore)
                                }
                            }
                            // funzione che si attiva in caso di fallimento
                            override fun onFailure(call: okhttp3.Call, e: IOException) {
                                Log.d("MainActivity", "onFailure: "+e.message)
                            }
                        })
                    }
                }.addOnFailureListener { e ->
                    Toast.makeText(
                        this@MainActivity,
                        "Error " + "${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(
                this@MainActivity,
                "Error: " + "${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

