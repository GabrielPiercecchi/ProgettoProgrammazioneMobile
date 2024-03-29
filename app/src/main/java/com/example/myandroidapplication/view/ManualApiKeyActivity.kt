package com.example.myandroidapplication.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myandroidapplication.R
import com.example.myandroidapplication.viewModel.util.Constants.Companion.API_KEY
import com.example.myandroidapplication.viewModel.util.NetworkUtils
import com.example.myandroidapplication.viewModel.util.MethodsUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ManualApiKeyActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    private lateinit var edtApiKey: EditText
    private lateinit var btnInsert: Button

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apikey)

        if (!NetworkUtils.isInternetAvailable(this)) {
            NetworkUtils.showNoInternetDialog(this)
        }

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference

        edtApiKey = findViewById(R.id.edit_apikey)
        btnInsert = findViewById(R.id.apiSubmit)

        edtApiKey.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // Calcola l'altezza desiderata in base al contenuto del testo
                val layoutParams = edtApiKey.layoutParams
                val lineCount = edtApiKey.lineCount
                val lineHeight = edtApiKey.lineHeight
                val extraHeight = MethodsUtils.dpToPx(29) // Usa la funzione dpToPx dalla classe di utilità
                val desiredHeight = (lineCount * lineHeight) + extraHeight

                // Imposta l'altezza desiderata
                layoutParams.height = desiredHeight
                edtApiKey.layoutParams = layoutParams
            }
        })

        btnInsert.setOnClickListener{
            try {
                // Rimuovi eventuali spazi vuoti all'inizio e alla fine
                val newApiKey = edtApiKey.text.toString().trim()
                if (newApiKey.isNotEmpty()){
                    API_KEY = newApiKey
                    val currentUser = mAuth.currentUser
                    currentUser?.let {
                        val uid = it.uid
                        MethodsUtils.updateUserApiKey(uid, newApiKey)
                    }
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                } else {
                    API_KEY = ""
                    Toast.makeText(this@ManualApiKeyActivity,
                        "Error: The new API KEY can't be empty",
                        Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception){
                Toast.makeText(
                    this@ManualApiKeyActivity,
                    "Error: " + "${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}