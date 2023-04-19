package com.example.myandroidapplication.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myandroidapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.auth.User

class ForgotPassword : AppCompatActivity() {

    private lateinit var editBox: EditText
    private lateinit var btnReset: Button

    private lateinit var mAuth: FirebaseAuth

    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_f_password)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        editBox = findViewById(R.id.editBox)
        btnReset = findViewById(R.id.btnReset)


        btnReset.setOnClickListener {
            val emailRPassword = editBox.text.toString()
            checkEmail(emailRPassword)

//            mAuth.sendPasswordResetEmail(emailRPassword)
//                .addOnSuccessListener {
//                    Toast.makeText(this, "Please check your email", Toast.LENGTH_SHORT). show()
//                }
//                .addOnCompleteListener {
//                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
//                }
        }
    }
    private fun checkEmail(emailRPassword: String){
        mAuth.sendPasswordResetEmail(emailRPassword)

        //check in an user is signed in
        val databaseRef = FirebaseDatabase.getInstance().getReference("user")
        val query = databaseRef.orderByChild("email").equalTo(emailRPassword.toString())

        query.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // L'email è presente nel database
                    mAuth.sendPasswordResetEmail(emailRPassword)
                        .addOnSuccessListener {
                            Toast.makeText(this@ForgotPassword, "Please check your email",
                                Toast.LENGTH_SHORT).show()
                        }
                        .addOnCompleteListener {
                            Toast.makeText(this@ForgotPassword, it.toString(),
                                Toast.LENGTH_SHORT).show()
                        }
                } else {
                    // L'email non è presente nel database
                    Toast.makeText(this@ForgotPassword, "The email doesn't exist",
                        Toast.LENGTH_SHORT).show()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Errore nel recupero dei dati dal database
                Toast.makeText(
                    this@ForgotPassword,
                    "Error: $error",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}