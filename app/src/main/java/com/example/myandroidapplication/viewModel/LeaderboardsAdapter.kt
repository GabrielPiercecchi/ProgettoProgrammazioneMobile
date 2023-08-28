package com.example.myandroidapplication.viewModel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myandroidapplication.databinding.LeaderboardRowBinding
import com.example.myandroidapplication.model.Clan
import com.example.myandroidapplication.model.Clans
import com.example.myandroidapplication.model.Player
import com.example.myandroidapplication.model.Players

class LeaderboardsAdapter (val players: Players) : RecyclerView.Adapter<LeaderboardsAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(val v: LeaderboardRowBinding): RecyclerView.ViewHolder (v.root)

    override fun getItemCount(): Int {
        return players.items?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val v = LeaderboardRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(v)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentPlayer = players.items?.getOrNull(position)
        currentPlayer?.let {
            holder.v.tvPlayerTag.text = "TAG: ${it.tag ?: "N/A"}"
            holder.v.tvPlayerName.text = "Name: ${it.name ?: "N/A"}"
        }
    }

}

class ClansAdapter (val clans: Clans) : RecyclerView.Adapter<ClansAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(val v: LeaderboardRowBinding): RecyclerView.ViewHolder (v.root)

    override fun getItemCount(): Int {
        return clans.items?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val v = LeaderboardRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(v)
    }


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentClan = clans.items?.getOrNull(position)
        currentClan?.let {
            holder.v.tvPlayerTag.text = ("TAG: " + it.tag) ?: "N/A"
            holder.v.tvPlayerName.text = ("Name: " + it.name) ?: "N/A"
        }
    }
}