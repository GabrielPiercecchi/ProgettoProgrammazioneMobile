package com.example.myandroidapplication.view

import android.content.Intent
import android.content.res.Resources
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

        if (!NetworkUtils.isInternetAvailable(this)) {
            NetworkUtils.showNoInternetDialog(this)
        }

        mAuth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.edit_email)
        edtPassword = findViewById(R.id.edit_password)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignUp)
        btnFPassword = findViewById(R.id.btnFPassword)
        txtTutorial = findViewById(R.id.txtTutorial)

        edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // Calcola l'altezza desiderata in base al contenuto del testo
                val layoutParams = edtEmail.layoutParams
                val lineCount = edtEmail.lineCount
                val lineHeight = edtEmail.lineHeight
                val extraHeight = 29.dpToPx() // Aggiungi 16 dp all'altezza
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
                val extraHeight = 29.dpToPx() // Aggiungi 16 dp all'altezza
                val desiredHeight = (lineCount * lineHeight) + extraHeight

                // Imposta l'altezza desiderata
                layoutParams.height = desiredHeight
                edtPassword.layoutParams = layoutParams
            }
        })

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

    // Estensione per convertire dp in px
    fun Int.dpToPx(): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (this * scale + 0.5f).toInt()
    }
}
