package com.example.myandroidapplication.util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity

object NetworkUtils {

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    fun showNoInternetDialog(activity: AppCompatActivity) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Connessione Internet assente")
        builder.setMessage("Non è disponibile una connessione Internet. Controlla la tua connessione e scegli un'opzione.")

        // Aggiungi il pulsante "Riprova"
        builder.setPositiveButton("Riprova") { dialog, _ ->
            // Ricarica l'activity con il suo onCreate
            val intent = Intent(activity, activity::class.java)
            activity.finish()
            activity.startActivity(intent)
        }

        // Aggiungi il pulsante "Annulla"
        builder.setNeutralButton("Annulla") { dialog, _ ->
            dialog.dismiss()
        }

        // Aggiungi il pulsante "Chiudi"
        builder.setNegativeButton("Chiudi") { dialog, _ ->
            dialog.dismiss()
            activity.finish()
        }

        builder.setCancelable(false)
        builder.show()
    }
}
