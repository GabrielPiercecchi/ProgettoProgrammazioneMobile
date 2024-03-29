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
import com.example.myandroidapplication.model.User
import com.example.myandroidapplication.R
import com.example.myandroidapplication.viewModel.util.NetworkUtils
import com.example.myandroidapplication.viewModel.util.MethodsUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.view.Gravity


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

        edtTag.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // Calcola l'altezza desiderata in base al contenuto del testo
                val layoutParams = edtTag.layoutParams
                val lineCount = edtTag.lineCount
                val lineHeight = edtTag.lineHeight
                val extraHeight = MethodsUtils.dpToPx(29) // Usa la funzione dpToPx dalla classe di utilità
                val desiredHeight = (lineCount * lineHeight) + extraHeight

                // Imposta l'altezza desiderata
                layoutParams.height = desiredHeight
                edtTag.layoutParams = layoutParams
            }
        })

        edtName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // Calcola l'altezza desiderata in base al contenuto del testo
                val layoutParams = edtName.layoutParams
                val lineCount = edtName.lineCount
                val lineHeight = edtName.lineHeight
                val extraHeight = MethodsUtils.dpToPx(29) // Usa la funzione dpToPx dalla classe di utilità
                val desiredHeight = (lineCount * lineHeight) + extraHeight

                // Imposta l'altezza desiderata
                layoutParams.height = desiredHeight
                edtName.layoutParams = layoutParams
            }
        })

        edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // Calcola l'altezza desiderata in base al contenuto del testo
                val layoutParams = edtEmail.layoutParams
                val lineCount = edtEmail.lineCount
                val lineHeight = edtEmail.lineHeight
                val extraHeight = MethodsUtils.dpToPx(29) // Usa la funzione dpToPx dalla classe di utilità
                val desiredHeight = (lineCount * lineHeight) + extraHeight

                // Imposta l'altezza desiderata
                layoutParams.height = desiredHeight
                edtEmail.layoutParams = layoutParams
            }
        })

        edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // Calcola l'altezza desiderata in base al contenuto del testo
                val layoutParams = edtPassword.layoutParams
                val lineCount = edtPassword.lineCount
                val lineHeight = edtPassword.lineHeight
                val extraHeight = MethodsUtils.dpToPx(29) // Usa la funzione dpToPx dalla classe di utilità
                val desiredHeight = (lineCount * lineHeight) + extraHeight

                // Imposta l'altezza desiderata
                layoutParams.height = desiredHeight
                edtPassword.layoutParams = layoutParams
            }
        })

        confirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // Calcola l'altezza desiderata in base al contenuto del testo
                val layoutParams = confirmPassword.layoutParams
                val lineCount = confirmPassword.lineCount
                val lineHeight = confirmPassword.lineHeight
                val extraHeight = MethodsUtils.dpToPx(29) // Usa la funzione dpToPx dalla classe di utilità
                val desiredHeight = (lineCount * lineHeight) + extraHeight

                // Imposta l'altezza desiderata
                layoutParams.height = desiredHeight
                confirmPassword.layoutParams = layoutParams
            }
        })

        btnSignUp.setOnClickListener{
            try {
                val tag = edtTag.text.toString()
                val name = edtName.text.toString()
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                val confirmPassword = confirmPassword.text.toString()

                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    // Mostra un messaggio di errore se uno dei campi è vuoto
                    Toast.makeText(
                        this@SignUp,
                        "Error: All fields are mandatory except for the player TAG ",
                        Toast.LENGTH_LONG
                    ).apply {
                        setGravity(Gravity.BOTTOM, 0, 0) // Imposta la posizione del Toast in basso
                    }.show()
                } else if (password != confirmPassword) {
                    // Mostra un messaggio di errore se le password non corrispondono
                    Toast.makeText(
                        this@SignUp,
                        "Error: Password Mismatch",
                        Toast.LENGTH_LONG
                    ).show()
                } else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    // Mostra un messaggio di errore se e-mail non ha la "@"
                    Toast.makeText(
                        this@SignUp,
                        "Error: Invalid e-mail address",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    // Effettua la registrazione se tutti i controlli passano
                    signUp(tag, name, email, password)
                }
            } catch (e: Exception){
                Toast.makeText(
                    this@SignUp,
                    "Error: " + "${e.message}",
                    Toast.LENGTH_LONG
                ).apply {
                    setGravity(Gravity.BOTTOM, 0, 0) // Imposta la posizione del Toast in basso
                }.show()
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
                    MethodsUtils.addUserToDatabase(tag, name, email, mAuth.currentUser?.uid!!)
                    val intent =Intent(this@SignUp,
                        MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        this@SignUp,
                        "Error: " + task.exception.toString(),
                        Toast.LENGTH_LONG
                    ).apply {
                        setGravity(Gravity.BOTTOM, 0, 0) // Imposta la posizione del Toast in basso
                    }.show()
                }
            }
    }
}