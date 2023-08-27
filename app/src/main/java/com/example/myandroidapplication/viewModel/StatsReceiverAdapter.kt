package com.example.myandroidapplication.viewModel

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myandroidapplication.databinding.SingleRowBinding
import com.example.myandroidapplication.model.Label
import com.example.myandroidapplication.model.Player
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class StatsReceiverAdapter(val players: Player?): RecyclerView.Adapter<StatsReceiverAdapter.CustomViewHolder>() {

    // Variabile utilizzata per le autenticazioni Firebase
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    inner class CustomViewHolder(val v: SingleRowBinding): RecyclerView.ViewHolder (v.root)

    override fun getItemCount(): Int {
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val v = SingleRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(v)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference

        try {
            val players = this.players
            if (isPlayerDataValid(players)) {
                with(holder.v) {

                    // Aggiunta di un ValueEventListener per ottenere il nome dell'utente dal database
                    if (players != null) {
                        mDbRef.child("user").orderByChild("tag").equalTo(players?.tag).get().addOnSuccessListener { snapshot ->
                            if (snapshot.exists()) {
                                // Otteniamo il primo figlio trovato che corrisponde al tag
                                val userSnapshot = snapshot.children.first()
                                val name = userSnapshot.child("name").getValue(String::class.java) ?: ""
                                holder.v.textTitleName.text = "Statistiche di $name"
                            }
                        }
                    }


                    tvBestTrophies.text = "Best Trophies: " + players?.bestTrophies.toString()
                    tvBestVersusTrophies.text = "Best Versus Trophies: " + players?.bestVersusTrophies.toString()
                    tvBuilderHallLevel.text = "Builder Hall Level: " + players?.builderHallLevel.toString()

                    val clanName = players?.clan?.name ?: ""
                    tvClan.text = "Clan name: " + clanName
                    // Carica l'immagine del clan dalla URL "small"
                    val clanSmallIconUrl = players?.clan?.badgeUrls?.small
                    if (!clanSmallIconUrl.isNullOrEmpty()) {
                        Glide.with(ivClan.context).load(clanSmallIconUrl).into(ivClan)
                    }

                    tvDonations.text = "Donations: " + players?.donations.toString()
                    tvDonationsReceived.text = "Donations Received: " + players?.donationsReceived.toString()
                    tvExpLevel.text = "Exp Level: " + players?.expLevel.toString()

                    val labelsArray = players?.labels
                    val labelsText = labelsArray?.let { parseLabels(it) }
                    tvLabel.text = "Labels: " + labelsText
                    val labelSmallUrls = labelsArray?.let { extractSmallUrls(it) }
                    if (players?.labels != null) {
                        for (i in 0 until players.labels.size) {
                            if (labelSmallUrls != null && i < labelSmallUrls.size) {
                                val labelIconUrl = labelSmallUrls[i]
                                when (i) {
                                    0 -> Glide.with(ivLabel1.context).load(labelIconUrl).into(ivLabel1)
                                    1 -> Glide.with(ivLabel2.context).load(labelIconUrl).into(ivLabel2)
                                    2 -> Glide.with(ivLabel3.context).load(labelIconUrl).into(ivLabel3)
                                }
                            }
                        }
                    }

                    val leagueName = players?.league?.name ?: ""
                    tvLeague.text = "League Name: " + leagueName
                    Glide.with(ivLeague.context).load(players?.league?.iconUrls?.small).into(ivLeague)

                    val playerName = players?.name ?: ""
                    tvName.text = "Player name: " + playerName
                    tvRole.text = "Role: " + players?.role ?: ""
                    tvTag.text = "Tag: " + players?.tag ?: ""
                    tvTownHallLevel.text = "Town Hall Level: " + players?.townHallLevel.toString()
                    tvTrophies.text = "Trophies: " + players?.trophies.toString()
                    tvWarStars.text = "War Stars: " + players?.warStars.toString()
                }
            } else {
                // Gestisci la situazione in cui 'players' è nullo
                // Ad esempio, puoi impostare dei valori di default o mostrare un messaggio di errore
                holder.v.tvBestTrophies.text = "No player data available!!\n" +
                        "Go to <<Api Key>> and submit a valid key.\n" +
                        "If you don't know how to do it check te <<Tutorial>>.\n" +
                        "If this error persist it can be from the Player's Wrong TAG."
            }
        } catch (e: Error) {
            println(e)
        }
    }

    private fun isPlayerDataValid(players: Player?): Boolean {
        return players != null &&
                players.bestTrophies != null &&
                players.bestVersusTrophies != null &&
                players.builderHallLevel != null &&
                players.donations != null &&
                players.donationsReceived != null &&
                players.expLevel != null &&
                players.clan != null &&
                players.labels != null && players.labels.size >= 3 &&
                players.league != null &&
                players.name != null &&
                players.role != null &&
                players.tag != null &&
                players.townHallLevel != null &&
                players.trophies != null
    }

    private fun parseLabels(labelsArray: List<Label>): String {
        val parsedLabels = mutableListOf<String>()
        for (label in labelsArray) {
            parsedLabels.add(label.name)
        }
        return parsedLabels.joinToString(", ")
    }

    private fun extractSmallUrls(labelsArray: List<Label>): MutableList<String> {
        val smallUrls = mutableListOf<String>()
        for (label in labelsArray) {
            label.iconUrls?.small?.let {
                smallUrls.add(it)
            }
        }
        return smallUrls
    }
}