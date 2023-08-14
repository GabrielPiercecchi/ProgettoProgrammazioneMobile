package com.example.myandroidapplication.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myandroidapplication.databinding.LeaderboardRowBinding
import com.example.myandroidapplication.model.Players

class LeaderboardsAdapter (val playersLeaderboards: Players) : RecyclerView.Adapter<LeaderboardsAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(val v: LeaderboardRowBinding): RecyclerView.ViewHolder (v.root)

    override fun getItemCount(): Int {
        return playersLeaderboards.players.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val v = LeaderboardRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(v)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val p = playersLeaderboards.players[position]
        holder.v.tvPlayerName.text = p.name
    }
}