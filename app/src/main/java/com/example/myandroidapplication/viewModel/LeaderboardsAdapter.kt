package com.example.myandroidapplication.viewModel

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myandroidapplication.databinding.LeaderboardRowBinding
import com.example.myandroidapplication.model.Clans
import com.example.myandroidapplication.model.Players
import com.example.myandroidapplication.view.ClanStatsReceiver
import com.example.myandroidapplication.view.StatsReceiver

class LeaderboardsAdapter (val players: Players) : RecyclerView.Adapter<LeaderboardsAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(val v: LeaderboardRowBinding): RecyclerView.ViewHolder (v.root)

    override fun getItemCount(): Int {
        return players.items.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val v = LeaderboardRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(v)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentPlayer = players.items.getOrNull(position)
        currentPlayer?.let {
            holder.v.tvPlayerTag.text = "TAG: ${it.tag ?: "N/A"}"
            holder.v.tvPlayerName.text = "Name: ${it.name ?: "N/A"}"
        }

        // La logica dovrebbe essere questa...
        holder.v.clickableItem.setOnClickListener {
            // Qui devo vedere come passare il contesto corretto che è quello l'errore
            val intent = Intent(it.context, StatsReceiver::class.java)
            if (currentPlayer != null) {
                intent.putExtra("tag", currentPlayer.tag)
            }
            if (currentPlayer != null) {
                intent.putExtra("name", currentPlayer.name)
            }
            it.context.startActivity(intent)
        }
    }
}
class ClansAdapter (val clans: Clans) : RecyclerView.Adapter<ClansAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(val v: LeaderboardRowBinding): RecyclerView.ViewHolder (v.root)

    override fun getItemCount(): Int {
        return clans.items.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val v = LeaderboardRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(v)
    }


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentClan = clans.items?.getOrNull(position)
        currentClan?.let { it ->
            holder.v.tvPlayerTag.text = ("TAG: " + it.tag) ?: "N/A"
            holder.v.tvPlayerName.text = ("Name: " + it.name) ?: "N/A"

            holder.v.clickableItem.setOnClickListener{
                val intent = Intent(it.context, ClanStatsReceiver::class.java)
                if (currentClan != null) {
                    intent.putExtra("tag", currentClan.tag)
                }
                if (currentClan != null) {
                    intent.putExtra("name", currentClan.name)
                }
                it.context.startActivity(intent)
            }
        }
    }
}