package com.example.myandroidapplication.viewModel.util

import android.content.res.Resources

class Constants {
    companion object{
        // Setta la API KEY per effettuare tutte le richieste
        var API_KEY = ""
        /* Per ora lascio il player tag in questo modo per motivi di test, successivamente va messo
           il tag del giocatore registrato */
        private const val PLAYER_TAG = "%23PGJVQUJJL"
        val PLAYERS_URL = "https://api.clashofclans.com/v1/players/"

        // URL per i vari ranking
        var LOCATION_ID = "" // 32000000 - Europe (per accedere alle altre cambia le ultime cifre!)
        val GET_LOCATIONS = "https://api.clashofclans.com/v1/locations"
        val GET_CLAN = "https://api.clashofclans.com/v1/clans/"
        val RANKING_CLANS_NORMAL = "https://api.clashofclans.com/v1/locations/$LOCATION_ID/rankings/clans"
        val RANKING_CLANS_BUILDER = "https://api.clashofclans.com/v1/locations/$LOCATION_ID/rankings/clans-versus"
        val RANKING_CLANS_CAPITAL = "https://api.clashofclans.com/v1/locations/$LOCATION_ID/rankings/capitals"
        val RANKING_PLAYERS_NORMAL = "https://api.clashofclans.com/v1/locations/$LOCATION_ID/rankings/players"
        val RANKING_PLAYERS_BUILDER = "https://api.clashofclans.com/v1/locations/$LOCATION_ID/rankings/players-builder-base"
    }

    // Estensione per convertire dp in px
}

object MethodsUtils {
    fun dpToPx(dp: Int): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }
}