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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.myandroidapplication.viewModel.util.MethodsUtils


class ChangeTag : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    private lateinit var userTagTxt: TextView
    private lateinit var edtTag: EditText
    private lateinit var changeTagBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_tag)

        if (!NetworkUtils.isInternetAvailable(this)) {
            NetworkUtils.showNoInternetDialog(this)
        }

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference

        userTagTxt = findViewById<TextView>(R.id.userTag)
        edtTag = findViewById<EditText>(R.id.edit_user_tag)
        changeTagBtn = findViewById<Button>(R.id.userTagSubmit)

        val currentUser = mAuth.currentUser

        // Aggiunta di un ValueEventListener per ottenere il nome dell'utente dal database
        currentUser?.let {
            val uid = it.uid
            mDbRef.child("user").child(uid).get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val name = snapshot.child("name").getValue(String::class.java) ?: ""
                    val tag = snapshot.child("tag").getValue(String::class.java) ?: ""

                    // Imposta il tag dell'utente come testo predefinito nell'EditText
                    edtTag.hint = "Old Tag: $tag"

                    val testo = "If you have noticed that you entered your TAG incorrectly, you can " +
                            "correct it by entering the correct one below and pressing the " +
                            "confirmation button!!"

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

                    userTagTxt.text = testo.replace("name_placeholder", name)
                }
            }.addOnFailureListener { e ->
                Toast.makeText(
                    this@ChangeTag,
                    "Error " + "${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        changeTagBtn.setOnClickListener {
            val newTag = edtTag.text.toString()
            if (newTag.isNotEmpty()) {
                val currentUser = mAuth.currentUser
                currentUser?.let {
                    val uid = it.uid
                    updateUserTag(uid, newTag)
                }
                startActivity(Intent(applicationContext, MainActivity::class.java))
            } else {
                Toast.makeText(this, "The new TAG can't be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUserTag(uid: String, tag: String) {
        mDbRef.child("user").child(uid).child("tag").setValue(tag)
    }
}
