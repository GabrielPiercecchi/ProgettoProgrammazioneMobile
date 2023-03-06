package com.example.myandroidapplication

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


// Classe per la schermata di Sign Up
class SignUp : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignUp: Button

    // Variabile utilizzata per le autenticazioni Firebase
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        edtName = findViewById(R.id.edit_name)
        edtEmail = findViewById(R.id.edit_email)
        edtPassword = findViewById(R.id.edit_password)
        btnSignUp = findViewById(R.id.btnSignUp)

        btnSignUp.setOnClickListener{
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            signUp(email, password)
        }
    }

    private fun signUp(email: String, password: String){
        // Logica per la creazione di utenti
//        mAuth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(
//                this
//            ) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    val intent =Intent(this@SignUp, MainActivity::class.java)
////                    startActivity(intent)
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Toast.makeText(
//                        this@SignUp,
//                        "Error" + task.exception.toString(),
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            }

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Codice per tornare alla home

                    val intent =Intent(this@SignUp, MainActivity::class.java)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        this@SignUp,
                        "Error" + task.exception.toString(),
                        Toast.LENGTH_LONG
                    ).show()
//                    Toast.makeText(this@SignUp, "E' successo qualcosa",
//                    Toast.LENGTH_SHORT).show()
                }
            }
    }
}