package com.example.myandroidapplication.viewModel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myandroidapplication.R
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

    var isLoggedIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        //loadTheme()

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

        btnLogin.setOnClickListener {
            try {
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()

                login(email, password)
            } catch (e: Exception) {
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

    fun login(email: String, password: String) {
        // Logica per l'accesso degli utenti già iscritti

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Codice per loggare

                    val intent = Intent(
                        this@Login,
                        MainActivity::class.java
                    )
                    //finish()
                    isLoggedIn = true
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
