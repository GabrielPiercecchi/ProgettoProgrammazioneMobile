package com.example.myandroidapplication.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myandroidapplication.R
import com.google.firebase.auth.FirebaseAuth
import okhttp3.internal.http2.StreamResetException

// Classe per la schermata di login
class MainActivity : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnChat: Button
    private lateinit var btnSignUp: Button
    private lateinit var btnFPassword: Button

    // Variabile utilizzata per le autenticazioni Firebase
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
    }
}
