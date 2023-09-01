package com.example.myandroidapplication.viewModel

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myandroidapplication.R
import com.example.myandroidapplication.util.NetworkUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


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
                    edtTag.hint = "Vecchio Tag: $tag"

                    val testo = "Se ti sei accorto di aver inserito il tuo TAG in maniera errata puoi " +
                            "modificarlo inserendo quello corretto qua sotto e premendo il " +
                            "bottone di conferma!!"
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
                Toast.makeText(this, "Il nuovo tag non può essere vuoto", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUserTag(uid: String, tag: String) {
        mDbRef.child("user").child(uid).child("tag").setValue(tag)
    }

//    private fun updateUserTag(uid: String, tag: String, name: String, email: String) {
//        mDbRef.child("user").child(uid).setValue(User(tag, name, email, uid))
//    }
}
