package com.example.myandroidapplication.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myandroidapplication.R
import com.example.myandroidapplication.viewModel.HomeFragment
import com.example.myandroidapplication.viewModel.ManualApiKeyActivity
import com.google.firebase.auth.FirebaseAuth
import okhttp3.internal.http2.StreamResetException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

// Classe per la schermata di login
class Login : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button
    private lateinit var btnFPassword: Button

    // Variabile utilizzata per le autenticazioni Firebase
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.edit_email)
        edtPassword = findViewById(R.id.edit_password)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignUp)
        btnFPassword = findViewById(R.id.btnFPassword)

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
