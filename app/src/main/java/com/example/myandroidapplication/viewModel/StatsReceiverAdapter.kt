package com.example.myandroidapplication.viewModel

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myandroidapplication.R
import com.example.myandroidapplication.databinding.SingleClanRowBinding
import com.example.myandroidapplication.databinding.SingleRowBinding
import com.example.myandroidapplication.model.ClanExtended
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
            if (players?.tag != null) {
                with(holder.v) {

                    try {
                        // Aggiunta di un ValueEventListener per ottenere il nome dell'utente dal database
                        mDbRef.child("user").orderByChild("tag").equalTo(players?.tag).get().addOnSuccessListener { snapshot ->
                            if (snapshot.exists()) {
                                // Otteniamo il primo figlio trovato che corrisponde al tag
                                val userSnapshot = snapshot.children.first()
                                val name = userSnapshot.child("name").getValue(String::class.java) ?: ""
                                holder.v.textTitleName.text = "Statistics of $name"
                            } else {
                                holder.v.textTitleName.text = "Statistics"
                            }
                        }
                    } catch (e: Exception) {
                        holder.v.textTitleName.text = "Statistics"
                    }

                    // Show "N/D" for null values
                    tvBestTrophies.text = "Best Trophies: " + (players?.bestTrophies ?: "N/D")
                    tvBestVersusTrophies.text = "Best Versus Trophies: " + (players?.bestVersusTrophies ?: "N/D")
                    tvBuilderHallLevel.text = "Builder Hall Level: " + (players?.builderHallLevel ?: "N/D")

                    val clanName = players?.clan?.name ?: "N/D"
                    tvClan.text = "Clan name: " + clanName
                    // Carica l'immagine del clan dalla URL "small"
                    val clanSmallIconUrl = players?.clan?.badgeUrls?.small
                    if (!clanSmallIconUrl.isNullOrEmpty()) {
                        Glide.with(ivClan.context).load(clanSmallIconUrl).into(ivClan)
                    }

                    tvDonations.text = "Donations: " + (players?.donations ?: "N/D")
                    tvDonationsReceived.text = "Donations Received: " + (players?.donationsReceived ?: "N/D")
                    tvExpLevel.text = "Exp Level: " + (players?.expLevel?: "N/D")

                    val labelsArray = players?.labels
                    val labelsText = labelsArray?.let { parseLabels(it) }
                    tvLabel.text = "Labels: " + (labelsText?: "N/D")
                    val labelSmallUrls = extractSmallUrls(players?.labels ?: emptyList())
                    val labelImageViews = listOf(ivLabel1, ivLabel2, ivLabel3)
                    for (i in labelImageViews.indices) {
                        if (i < labelSmallUrls.size) {
                            val labelIconUrl = labelSmallUrls[i]
                            if (!labelIconUrl.isNullOrEmpty()) {
                                Glide.with(labelImageViews[i].context).load(labelIconUrl).into(labelImageViews[i])
                            }
                        }
                    }

                    val league = players?.league
                    val leagueName = league?.name ?: "N/D"
                    tvLeague.text = "League Name: $leagueName"

                    val leagueIconUrl = league?.iconUrls?.small
                    if (!leagueIconUrl.isNullOrEmpty()) {
                        Glide.with(ivLeague.context).load(leagueIconUrl).into(ivLeague)
                    }
                    val playerName = players?.name ?: "N/D"
                    tvName.text = "Player name: " + playerName
                    tvRole.text = "Role: " + (players?.role ?: "N/D")
                    tvTag.text = "Tag: " + (players?.tag ?: "N/D")
                    tvTownHallLevel.text = "Town Hall Level: " + (players?.townHallLevel?: "N/D")
                    tvTrophies.text = "Trophies: " + (players?.trophies?: "N/D")
                    tvWarStars.text = "War Stars: " + (players?.warStars?: "N/D")
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


class ClanStatsReceiverAdapter(val clan: ClanExtended?): RecyclerView.Adapter<ClanStatsReceiverAdapter.CustomViewHolder>() {

    // Variabile utilizzata per le autenticazioni Firebase
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    inner class CustomViewHolder(val v: SingleClanRowBinding): RecyclerView.ViewHolder (v.root)

    override fun getItemCount(): Int {
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val v = SingleClanRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(v)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference

        try {
            val clan = this.clan
            if (clan?.tag != null) {
                with(holder.v) {

                    try {
                        // Aggiunta di un ValueEventListener per ottenere il nome dell'utente dal database
                        mDbRef.child("user").orderByChild("tag").equalTo(clan?.tag).get().addOnSuccessListener { snapshot ->
                            if (snapshot.exists()) {
                                // Otteniamo il primo figlio trovato che corrisponde al tag
                                val userSnapshot = snapshot.children.first()
                                val name = userSnapshot.child("name").getValue(String::class.java) ?: ""
                                holder.v.textTitleName.text = "Statistics of $name"
                            } else {
                                holder.v.textTitleName.text = "Statistics"
                            }
                        }
                    } catch (e: Exception) {
                        holder.v.textTitleName.text = "Statistics"
                    }

                    // Show "N/D" for null values
                    tvClanLevel.text = "Clan Level: " + (clan?.clanLevel ?: "N/D")
                    tvClanPoints.text = "Clan Points: " + (clan?.clanPoints ?: "N/D")
                    tvClanCapitalPoints.text = "Clan capital Points: " + (clan?.clanCapitalPoints ?: "N/D")

                    tvName.text = "Clan name: " + (clan?.name ?: "N/D")
                    // Carica l'immagine del clan dalla URL "small"
                    val clanSmallIconUrl = clan?.badgeUrls?.small
                    if (!clanSmallIconUrl.isNullOrEmpty()) {
                        Glide.with(ivClan.context).load(clanSmallIconUrl).into(ivClan)
                    }

                    tvDescription.text = "Description: " + (clan?.description ?: "N/D")
                    tvTag.text = "TAG: " + (clan?.tag ?: "N/D")
                    tvType.text = "Type: " + (clan?.type?: "N/D")
                    tvMembers.text = "Members: " + (clan?.members?: "N/D")

                    val labelsArray = clan?.labels
                    val labelsText = labelsArray?.let { parseLabels(it) }
                    tvLabel.text = "Labels: " + (labelsText?: "N/D")
                    val labelSmallUrls = extractSmallUrls(clan?.labels ?: emptyList())
                    val labelImageViews = listOf(ivLabel1, ivLabel2, ivLabel3)
                    for (i in labelImageViews.indices) {
                        if (i < labelSmallUrls.size) {
                            val labelIconUrl = labelSmallUrls[i]
                            if (!labelIconUrl.isNullOrEmpty()) {
                                Glide.with(labelImageViews[i].context).load(labelIconUrl).into(labelImageViews[i])
                            }
                        }
                    }
                }
            } else {
                // Gestisci la situazione in cui 'players' è nullo
                // Ad esempio, puoi impostare dei valori di default o mostrare un messaggio di errore
                holder.v.tvTag.text = "No clan data available!!\n" +
                        "Go to <<Api Key>> and submit a valid key.\n" +
                        "If you don't know how to do it check te <<Tutorial>>.\n" +
                        "If this error persist it can be from the Clan's Wrong TAG."
            }
        } catch (e: Error) {
            println(e)
        }
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