package com.example.myandroidapplication.viewModel

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.myandroidapplication.R
import com.example.myandroidapplication.model.Player
import com.example.myandroidapplication.util.Constants
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getPlayer()
    }


    private fun getPlayer(){

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Constants.PLAYERS_URL)
            .addHeader("authorization","Bearer ${Constants.API_KEY}")
            .build()
        client.newCall(request).enqueue(object : okhttp3.Callback{
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if(response.isSuccessful) {
                    activity!!.runOnUiThread {
                        val responseBody = response.body?.string()

                        val gson = GsonBuilder().create()
                        val giocatore = gson.fromJson(responseBody, Player::class.java)

                        // Costruzione della string di risposta e applicazione della risposta alla vista
                        val txtId: TextView = view!!.findViewById(R.id.nav_home)
                        txtId.text = giocatore.toString()
                    }
                } else {
                    activity!!.runOnUiThread {
                        val txtId: TextView = view!!.findViewById(R.id.nav_home)
                        txtId.text = "Insert correctly the API KEY!"
                    }
                }
            }

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Log.d("MainActivity", "onFailure: "+e.message)
            }
        })
    }
}