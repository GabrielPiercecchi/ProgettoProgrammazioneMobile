package com.example.myandroidapplication.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.myandroidapplication.R
import com.example.myandroidapplication.viewModel.ManualApiKeyActivity
import com.example.myandroidapplication.viewModel.TutorialActivity
import com.google.firebase.auth.FirebaseAuth

// Classe per la schermata di login
class Login : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button
    private lateinit var btnFPassword: Button
    private lateinit var txtTutorial: TextView

    // Variabile utilizzata per le autenticazioni Firebase
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        val sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE)
        val nightMODE= sharedPreferences.getBoolean("dark", false)
        val editor: SharedPreferences.Editor

        if (nightMODE) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            editor = sharedPreferences.edit()
            editor.putBoolean("dark", false)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            editor = sharedPreferences.edit()
            editor.putBoolean("dark", true)
        }
        editor.apply()

        mAuth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.edit_email)
        edtPassword = findViewById(R.id.edit_password)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignUp)
        btnFPassword = findViewById(R.id.btnFPassword)
        txtTutorial = findViewById(R.id.txtTutorial)

        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener{
            try {
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()

                login(email, password)
            }catch (e: Exception){
                Toast.makeText(
                    this@Login,
                    "Error " + "${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }

        }

        btnFPassword.setOnClickListener {
            val intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
        }

        txtTutorial.setOnClickListener {
            val intent = Intent(this, TutorialActivity::class.java)
            startActivity(intent)
        }

    }

    private fun login(email: String, password: String){
        // Logica per l'accesso degli utenti già iscritti

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Codice per loggare

                    val intent =Intent(this@Login,
                        ManualApiKeyActivity::class.java)
                    //finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        this@Login,
                        "Error" + task.exception.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

}
