package com.example.myandroidapplication

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


// Classe per la schermata di Sign Up
class SignUp : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignUp: Button

    // Variabile utilizzata per le autenticazioni Firebase
    private lateinit var mAuth: FirebaseAuth

    private lateinit var mDbRef: DatabaseReference

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
            val name = edtName.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            signUp(name, email, password)
        }
    }

    private fun signUp(name:String, email: String, password: String){
        // Logica per la creazione di utenti

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Codice per tornare alla home
                    addUserToDatabase(name, email, mAuth.currentUser?.uid!!)
                    val intent =Intent(this@SignUp,
                        MainActivity::class.java)
                    finish()
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

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user")
            .child(uid).setValue(User(name, email, uid))
    }
}