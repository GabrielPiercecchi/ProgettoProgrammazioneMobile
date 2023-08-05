package com.example.myandroidapplication.viewModel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myandroidapplication.R
import com.example.myandroidapplication.util.Constants.Companion.API_KEY
import com.example.myandroidapplication.view.MainActivity

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
                API_KEY = edtApiKey.text.toString()
            } catch (e: Exception){
                Toast.makeText(
                    this@ManualApiKeyActivity,
                    "Error " + "${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }



    }
}