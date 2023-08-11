package com.example.myandroidapplication.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myandroidapplication.databinding.SingleRowBinding
import com.example.myandroidapplication.model.Player

class MainAdapter(val players: Player): RecyclerView.Adapter<MainAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(val v: SingleRowBinding): RecyclerView.ViewHolder (v.root)

    override fun getItemCount(): Int {
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val v = SingleRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(v)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {


        with(holder.v) {
            tvBestTrophies.text = players.bestTrophies.toString()
            tvBestVersusTrophies.text = players.bestVersusTrophies.toString()
            tvBuilderHallLevel.text = players.builderHallLevel.toString()
            tvClan.text = players.clan.name
            tvDonations.text = players.donations.toString()
            tvDonationsReceived.text = players.donationsReceived.toString()
            tvExpLevel.text = players.expLevel.toString()
            tvLabel.text = players.labels.toString()
            for (i in 0..2)
                Glide.with(ivLabel.context).load(players.labels[i].iconUrls.small).into(ivLabel)
            tvLeague.text = players.league.name
            Glide.with(ivLeague.context).load(players.league.iconUrls.small).into(ivLeague)
            tvName.text = players.name
            tvRole.text = players.role
            tvTag.text = players.tag
            tvTownHallLevel.text = players.townHallLevel.toString()
            tvTrophies.text = players.trophies.toString()
            tvWarStars.text = players.warStars.toString()
        }
    }
}