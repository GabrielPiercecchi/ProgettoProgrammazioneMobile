package com.example.myandroidapplication.viewModel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myandroidapplication.R
import com.example.myandroidapplication.util.Constants.Companion.API_KEY

class ManualApiKeyActivity : AppCompatActivity() {
    private lateinit var edtApiKey: EditText
    private lateinit var btnInsert: Button

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apikey)

        edtApiKey = findViewById(R.id.edit_apikey)
        btnInsert = findViewById(R.id.apiSubmit)

        btnInsert.setOnClickListener{
            try {
                // Rimuovi eventuali spazi vuoti all'inizio e alla fine
                val newApiKey = edtApiKey.text.toString().trim()
                if (newApiKey.isNotEmpty()){
                    API_KEY = newApiKey
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                } else {
                    API_KEY = ""
                    Toast.makeText(this@ManualApiKeyActivity,
                        "L'API key non può essere vuota",
                        Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception){
                Toast.makeText(
                    this@ManualApiKeyActivity,
                    "Error " + "${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }



    }
}