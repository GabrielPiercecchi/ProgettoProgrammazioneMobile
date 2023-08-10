package com.example.myandroidapplication.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myandroidapplication.databinding.SingleRowBinding
import com.example.myandroidapplication.model.Player

class MainAdapter(val players: Player): RecyclerView.Adapter<MainAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(val v: SingleRowBinding): RecyclerView.ViewHolder (v.root){
        /*fun bind(player: Player) {
            v.tvName.text = player.name
            v.tvTag.text = player.tag
            v.tvTrophies.text = player.trophies.toString()
        }*/
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val v = SingleRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(v)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        with(holder){
            v.tvName.text = players.name
            v.tvTag.text = players.tag
            v.tvTrophies.text = players.trophies.toString()
        }
    }
}