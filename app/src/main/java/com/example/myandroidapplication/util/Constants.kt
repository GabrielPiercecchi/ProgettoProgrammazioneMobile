package com.example.myandroidapplication.util

class Constants {
    companion object{
        // Setta la API KEY per effettuare tutte le richieste
        var API_KEY = ""

        /* Per ora lascio il player tag in questo modo per motivi di test, successivamente va messo
           il tag del giocatore registrato */
        private const val PLAYER_TAG = "%23PGJVQUJJL"
        const val PLAYERS_URL = "https://api.clashofclans.com/v1/players/$PLAYER_TAG"

        // URL per i vari ranking
        var LOCATION_ID = "" // 32000000 - Europe (per accedere alle altre cambia le ultime cifre!)
        val GET_LOCATIONS = "https://api.clashofclans.com/v1/locations"
        val RANKING_CLANS_NORMAL = "https://api.clashofclans.com/v1/locations/$LOCATION_ID/rankings/clans"
        val RANKING_CLANS_BUILDER = "https://api.clashofclans.com/v1/locations/$LOCATION_ID/rankings/clans-versus"
        val RANKING_CLANS_CAPITAL = "https://api.clashofclans.com/v1/locations/$LOCATION_ID/rankings/capitals"
        val RANKING_PLAYERS_NORMAL = "https://api.clashofclans.com/v1/locations/$LOCATION_ID/rankings/players"
        val RANKING_PLAYERS_BUILDER = "https://api.clashofclans.com/v1/locations/$LOCATION_ID/rankings/players-builder-base"
    }
}