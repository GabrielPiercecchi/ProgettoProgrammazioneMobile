package com.example.myandroidapplication.viewModel

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myandroidapplication.R
import com.example.myandroidapplication.model.Clan
import com.example.myandroidapplication.model.Clans
import com.example.myandroidapplication.model.Locations
import com.example.myandroidapplication.model.Player
import com.example.myandroidapplication.model.Players
import com.example.myandroidapplication.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.GsonBuilder
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


class LeaderboardsActivity : AppCompatActivity() {

    var itemFromSpinner: Pair<String, String> = Pair(" ", " ")
    var selectedItem: String = ""

    // Variabile utilizzata per le autenticazioni Firebase
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    private lateinit var statsSearchView: SearchView

    private lateinit var playersList: List<Player?> // Assicurati che Player sia il tuo modello per i giocatori
    private lateinit var clansList: List<Clan?> // Assicurati che Clan sia il tuo modello per i clan
    private var selectedRadioButtonId: Int =
        -1 // Valore iniziale, può essere modificato in base al tuo scenario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboards)

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()

        playersList = emptyList()
        clansList = emptyList()

        //Per SearchView
        statsSearchView = findViewById(R.id.statsSearchView)
        statsSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    filterRecyclerView(it)
                }
                return true
            }
        })

        //SEZIONE PER LA RECYCLER VIEW
        val rv_leaderboards = findViewById<RecyclerView>(R.id.rv_leaderboards)
        rv_leaderboards.layoutManager = LinearLayoutManager(this)

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
                        //
                        val correctUrl = Constants.PLAYERS_URL + tag

                        getAllLocations()

                        val rg: RadioGroup = findViewById(R.id.rg)

                        rg.setOnCheckedChangeListener { group, checkedId ->
                            selectedRadioButtonId = checkedId
                        }

                        val bUpdate: Button = findViewById(R.id.b_update_laed_69)

                        bUpdate.setOnClickListener {
                            when (rg.checkedRadioButtonId) {
                                R.id.rb_player, R.id.rb_builder, R.id.rb_clan -> {
                                    if (selectedItem.isNotBlank() && selectedItem != "Select Country") {
                                        when (rg.checkedRadioButtonId) {
                                            R.id.rb_player -> getPlayersNormalLeaderboardForLocation(
                                                selectedItem,
                                                apiKey
                                            )

                                            R.id.rb_builder -> getPlayersBuilderLeaderboardForLocation(
                                                selectedItem,
                                                apiKey
                                            )

                                            R.id.rb_clan -> getClansNormalLeaderboardForLocation(
                                                selectedItem,
                                                apiKey
                                            )
                                        }
                                    } else {
                                        Toast.makeText(
                                            this@LeaderboardsActivity,
                                            "Please select a valid location",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        }
                    }
                }.addOnFailureListener { e ->
                    Toast.makeText(
                        this@LeaderboardsActivity,
                        "Error " + "${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(
                this@LeaderboardsActivity,
                "Error " + "${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }


    private lateinit var adapterItems: ArrayAdapter<Pair<String, String>>

    // Funzione che ti permette di prendere tutte le locations e metterle nella select list.
    private fun getAllLocations() {
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

                        // costruzione dell'url e della richiesta HTTP
                        val client = OkHttpClient()
                        val request = Request.Builder()
                            .url(Constants.GET_LOCATIONS)
                            .addHeader("authorization", "Bearer $apiKey")
                            .build()
                        client.newCall(request).enqueue(object : okhttp3.Callback {
                            // funzione che si attiva in caso di una risposta
                            override fun onResponse(call: Call, response: okhttp3.Response) {
                                runOnUiThread {
                                    val responseBody = response.body?.string()

                                    val gson = GsonBuilder().create()
                                    val location =
                                        gson.fromJson(responseBody, Locations::class.java)

                                    val dataMap: MutableList<Pair<String, String>> =
                                        mutableListOf(Pair("Select Country", ""))
                                    dataMap.addAll(
                                        location.items?.drop(7)?.dropLast(5)
                                            ?.map { it.name to it.id.toString() } ?: emptyList())

                                    val spinner: Spinner = findViewById(R.id.spinner)

                                    try {
                                        adapterItems = ArrayAdapter<Pair<String, String>>(
                                            this@LeaderboardsActivity,
                                            android.R.layout.simple_spinner_dropdown_item,
                                            dataMap
                                        )
                                        spinner.adapter = adapterItems

                                        spinner.setOnItemSelectedListener(object :
                                            AdapterView.OnItemSelectedListener {
                                            override fun onItemSelected(
                                                parent: AdapterView<*>?,
                                                view: View?,
                                                position: Int,
                                                id: Long
                                            ) {
                                                itemFromSpinner =
                                                    parent?.getItemAtPosition(position) as Pair<String, String>
                                                selectedItem = itemFromSpinner.second
                                            }

                                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                                selectedItem =
                                                    "" // Resetto la selezione se non c'è nulla selezionato
                                            }
                                        })
                                    } catch (e: Exception) {
                                        Log.d("Error: ", "$e")
                                    }
                                }
                            }

                            // funzione che si attiva in caso di fallimento
                            override fun onFailure(call: Call, e: IOException) {
                                Log.d("MainActivity", "onFailure: " + e.message)
                            }
                        })
                    }
                }.addOnFailureListener { e ->
                    Toast.makeText(
                        this@LeaderboardsActivity,
                        "Error " + "${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(
                this@LeaderboardsActivity,
                "Error " + "${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }


    // Tira fuori la leaderboard dei giocatori della location selezionata
    //OK
    fun getPlayersNormalLeaderboardForLocation(locationid: String, apiKey: String) {
        // costruzione dell'url e della richiesta HTTP
        val client = OkHttpClient()
        val request = Request.Builder()
            //.url(Constants.RANKING_PLAYERS_NORMAL)
            .url("https://api.clashofclans.com/v1/locations/$locationid/rankings/players")
            .addHeader("authorization", "Bearer $apiKey")
            .build()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            // funzione che si attiva in caso di una risposta
            override fun onResponse(call: Call, response: okhttp3.Response) {
                runOnUiThread {
                    val responseBody = response.body?.string()

                    val gson = GsonBuilder().create()
                    val playersForLocation = gson.fromJson(responseBody, Players::class.java)

                    playersList = emptyList()
                    clansList = emptyList()
                    playersList = playersForLocation.items

//                    val dataMap = playersForLocation.players.take(playersForLocation.players.size).map{it.name}

                    val recyclerViewLeaderboards = findViewById<RecyclerView>(R.id.rv_leaderboards)
                    recyclerViewLeaderboards.adapter = LeaderboardsAdapter(playersForLocation)
                }
            }

            // Funzione che si attiva in caso di fallimento
            override fun onFailure(call: Call, e: IOException) {
                Log.d("LeaderboardsActivity", "onFailure: " + e.message)
            }
        })
    }

    //SU TEORIA OK
    fun getPlayersBuilderLeaderboardForLocation(locationid: String, apiKey: String) {
        // costruzione dell'url e della richiesta HTTP
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.clashofclans.com/v1/locations/$locationid/rankings/players-builder-base")
            .addHeader("authorization", "Bearer $apiKey")
            .build()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            // funzione che si attiva in caso di una risposta
            override fun onResponse(call: Call, response: okhttp3.Response) {
                runOnUiThread {
                    val responseBody = response.body?.string()

                    val gson = GsonBuilder().create()
                    val playersForLocation = gson.fromJson(responseBody, Players::class.java)

                    playersList = emptyList()
                    clansList = emptyList()
                    playersList = playersForLocation.items

                    val recyclerViewLeaderboards = findViewById<RecyclerView>(R.id.rv_leaderboards)
                    recyclerViewLeaderboards.adapter = LeaderboardsAdapter(playersForLocation)
                }
            }

            // Funzione che si attiva in caso di fallimento
            override fun onFailure(call: Call, e: IOException) {
                Log.d("MainActivity", "onFailure: " + e.message)
            }
        })
    }

    fun getClansNormalLeaderboardForLocation(locationid: String, apiKey: String) {
        // costruzione dell'url e della richiesta HTTP
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.clashofclans.com/v1/locations/$locationid/rankings/clans")
            .addHeader("authorization", "Bearer $apiKey")
            .build()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            // funzione che si attiva in caso di una risposta
            override fun onResponse(call: Call, response: okhttp3.Response) {
                runOnUiThread {
                    val responseBody = response.body?.string()

                    val gson = GsonBuilder().create()
                    val clansForLocation = gson.fromJson(responseBody, Clans::class.java)

                    playersList = emptyList()
                    clansList = emptyList()
                    clansList = clansForLocation.items

                    val recyclerViewLeaderboards = findViewById<RecyclerView>(R.id.rv_leaderboards)
                    recyclerViewLeaderboards.adapter = ClansAdapter(clansForLocation)
                }
            }

            // Funzione che si attiva in caso di fallimento
            override fun onFailure(call: Call, e: IOException) {
                Log.d("MainActivity", "onFailure: " + e.message)
            }
        })
    }

    // Aggiungi questo metodo alla classe LeaderboardsActivity
    private fun filterRecyclerView(query: String) {
        val filteredPlayersList = mutableListOf<Player>()
        val filteredClansList = mutableListOf<Clan>()

        for (player in playersList) {
            if (player != null) {
                if (player.tag.contains(query, ignoreCase = true) || player.name.contains(
                        query,
                        ignoreCase = true
                    )
                ) {
                    player?.let {filteredPlayersList.add(it)}
                }
            }
        }

        for (clan in clansList) {
            if (clan != null) {
                if (clan.tag.contains(query, ignoreCase = true) || clan.name.contains(
                        query,
                        ignoreCase = true
                    )
                ) {
                    clan?.let { filteredClansList.add(it) }
                }
            }
        }

        val filteredPlayersAdapter = LeaderboardsAdapter(Players(filteredPlayersList))
        val filteredClansAdapter = ClansAdapter(Clans(filteredClansList))

        val recyclerViewLeaderboards = findViewById<RecyclerView>(R.id.rv_leaderboards)
        if (selectedItem == "Select Country") {
            recyclerViewLeaderboards.adapter = null
        } else {
            when (selectedRadioButtonId) {
                R.id.rb_player -> recyclerViewLeaderboards.adapter = filteredPlayersAdapter
                R.id.rb_builder -> recyclerViewLeaderboards.adapter = filteredPlayersAdapter
                R.id.rb_clan -> recyclerViewLeaderboards.adapter = filteredClansAdapter
            }
        }

        if (filteredPlayersList.isEmpty() && filteredClansList.isEmpty()) {
            Toast.makeText(this@LeaderboardsActivity, "No Data Found", Toast.LENGTH_SHORT).show()
        } else {
            val filteredPlayersAdapter = LeaderboardsAdapter(Players(filteredPlayersList))
            val filteredClansAdapter = ClansAdapter(Clans(filteredClansList))

            val recyclerViewLeaderboards = findViewById<RecyclerView>(R.id.rv_leaderboards)
            if (selectedItem == "Select Country") {
                recyclerViewLeaderboards.adapter = null
            } else {
                when (selectedRadioButtonId) {
                    R.id.rb_player -> recyclerViewLeaderboards.adapter = filteredPlayersAdapter
                    R.id.rb_builder -> recyclerViewLeaderboards.adapter = filteredPlayersAdapter
                    R.id.rb_clan -> recyclerViewLeaderboards.adapter = filteredClansAdapter
                }
            }
        }
    }
}