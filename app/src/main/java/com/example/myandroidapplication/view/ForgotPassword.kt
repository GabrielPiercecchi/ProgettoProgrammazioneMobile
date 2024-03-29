package com.example.myandroidapplication.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myandroidapplication.R
import com.example.myandroidapplication.viewModel.util.NetworkUtils
import com.example.myandroidapplication.viewModel.util.MethodsUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ForgotPassword : AppCompatActivity() {

    private lateinit var editBox: EditText
    private lateinit var btnReset: Button

    private lateinit var mAuth: FirebaseAuth

    private lateinit var mDbRef: DatabaseReference

    private lateinit var txtTutorial: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_f_password)

        supportActionBar?.hide()

        if (!NetworkUtils.isInternetAvailable(this)) {
            NetworkUtils.showNoInternetDialog(this)
        }

        mAuth = FirebaseAuth.getInstance()

        editBox = findViewById(R.id.editBox)
        btnReset = findViewById(R.id.btnReset)
        txtTutorial = findViewById(R.id.txtTutorial)

        editBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // Calcola l'altezza desiderata in base al contenuto del testo
                val layoutParams = editBox.layoutParams
                val lineCount = editBox.lineCount
                val lineHeight = editBox.lineHeight
                val extraHeight = MethodsUtils.dpToPx(29) // Usa la funzione dpToPx dalla classe di utilità
                val desiredHeight = (lineCount * lineHeight) + extraHeight

                // Imposta l'altezza desiderata
                layoutParams.height = desiredHeight
                editBox.layoutParams = layoutParams
            }
        })

        btnReset.setOnClickListener {
            try {
                val emailRPassword = editBox.text.toString()
                checkEmail(emailRPassword)
            } catch (e: Exception){
                Toast.makeText(
                    this@ForgotPassword,
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

    // Serve per verificare che l'email inserita sia presente nel database
    // In caso esista procede all'invio del resetpassword
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