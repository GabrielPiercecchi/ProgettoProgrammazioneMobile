package com.example.myandroidapplication.viewModel.util

import android.content.res.Resources
import android.widget.Toast
import com.example.myandroidapplication.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Locale

class Constants {
    companion object{
        // Setta la API KEY per effettuare tutte le richieste
        var API_KEY = ""
        /* Per ora lascio il player tag in questo modo per motivi di test, successivamente va messo
           il tag del giocatore registrato */
        private const val PLAYER_TAG = "%23PGJVQUJJL"
        val PLAYERS_URL = "https://api.clashofclans.com/v1/players/"

        // URL per i vari ranking
//        var LOCATION_ID : String? = ""
         // 32000000 - Europe (per accedere alle altre cambia le ultime cifre!)
        val GET_LOCATIONS = "https://api.clashofclans.com/v1/locations"
        val GET_CLAN = "https://api.clashofclans.com/v1/clans/"
//        var RANKING_CLANS_NORMAL = "https://api.clashofclans.com/v1/locations/$LOCATION_ID/rankings/clans"
//        var RANKING_CLANS_BUILDER = "https://api.clashofclans.com/v1/locations/$LOCATION_ID/rankings/clans-versus"
//        var RANKING_CLANS_CAPITAL = "https://api.clashofclans.com/v1/locations/$LOCATION_ID/rankings/capitals"
//        var RANKING_PLAYERS_NORMAL = "https://api.clashofclans.com/v1/locations/$LOCATION_ID/rankings/players"
//        var RANKING_PLAYERS_BUILDER = "https://api.clashofclans.com/v1/locations/$LOCATION_ID/rankings/players-builder-base"
    }
}


// Questo object serve per collezioanre e richiamare tutti quei metodi statici presenti
// prima nelle view (per tenerle pulite il più possibile)
object MethodsUtils {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    // Estensione per convertire dp in px
    fun dpToPx(dp: Int): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    // Metodo per caricare il TAG aggiornato
    // Preso dalla classe ChangeTag
    fun updateUserTag(dbRef: DatabaseReference, uid: String, tag: String) {
        mDbRef = FirebaseDatabase.getInstance().reference
        dbRef.child("user").child(uid).child("tag").setValue(tag)
    }

    // Metodo per caricare la API KEY aggiornata
    // Preso dalla classe ManualApiKeyActivity
    fun updateUserApiKey(uid: String, apiKey: String) {
        mDbRef = FirebaseDatabase.getInstance().reference
        mDbRef.child("user").child(uid).child("apiKey").setValue(apiKey)
    }

    // Per aggiungere alla fine i dati del nuovo utente nel database
    // Preso dalla classe SignUp
    fun addUserToDatabase(tag: String, name: String, email: String, uid: String) {
        val apiKey = ""
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user")
            .child(uid).setValue(User(apiKey, tag, name, email, uid))
    }
}