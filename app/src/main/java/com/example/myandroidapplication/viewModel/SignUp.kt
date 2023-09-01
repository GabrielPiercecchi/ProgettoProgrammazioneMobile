package com.example.myandroidapplication.viewModel

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myandroidapplication.model.User
import com.example.myandroidapplication.R
import com.example.myandroidapplication.util.NetworkUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


// Classe per la schermata di Sign Up
class SignUp : AppCompatActivity() {

    private lateinit var edtTag: EditText
    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var btnSignUp: Button
    private lateinit var txtTutorial: TextView

    // Variabile utilizzata per le autenticazioni Firebase
    private lateinit var mAuth: FirebaseAuth
    //Variabile utilizzata per utilizzare il database di Firebase
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        if (!NetworkUtils.isInternetAvailable(this)) {
            NetworkUtils.showNoInternetDialog(this)
        }

        mAuth = FirebaseAuth.getInstance()

        edtTag = findViewById(R.id.edit_tag)
        edtName = findViewById(R.id.edit_name)
        edtEmail = findViewById(R.id.edit_email)
        edtPassword = findViewById(R.id.edit_password)
        confirmPassword = findViewById(R.id.confirm_password)
        btnSignUp = findViewById(R.id.btnSignUp)
        txtTutorial = findViewById(R.id.txtTutorial)

        btnSignUp.setOnClickListener{
            try {
                val tag = edtTag.text.toString()
                val name = edtName.text.toString()
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                val confirmPassword = confirmPassword.text.toString()

                if (password!=confirmPassword){
                    Toast.makeText(
                        this@SignUp,
                        "Error: " + "Password non uguali",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    signUp(tag, name, email, password)

                }
            } catch (e: Exception){
                Toast.makeText(
                    this@SignUp,
                    "Error " + "${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        txtTutorial.setOnClickListener {
            val intent = Intent(this, TutorialActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signUp(tag: String, name:String, email: String, password: String){
        // Logica per la creazione di utenti

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Codice per tornare alla home
                    addUserToDatabase(tag, name, email, mAuth.currentUser?.uid!!)
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

    //Per aggiungere alla fine i dati del nuovo utente nel database
    private fun addUserToDatabase(tag: String, name: String, email: String, uid: String) {
        val apiKey = ""
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user")
            .child(uid).setValue(User(apiKey, tag, name, email, uid))
    }
}