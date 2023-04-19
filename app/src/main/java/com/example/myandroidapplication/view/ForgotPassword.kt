package com.example.myandroidapplication.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myandroidapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

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
            val sPassword = editBox.text.toString()
            mAuth.sendPasswordResetEmail(sPassword)
                .addOnSuccessListener {
                 Toast.makeText(this, "Please check your email", Toast.LENGTH_SHORT). show()
                }
                .addOnCompleteListener {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                }

        }
    }
}