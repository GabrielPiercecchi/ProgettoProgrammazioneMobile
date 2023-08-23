package com.example.myandroidapplication.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myandroidapplication.model.User
import com.example.myandroidapplication.R
import com.example.myandroidapplication.viewModel.AboutActivity
import com.example.myandroidapplication.viewModel.SettingsActivity
import com.example.myandroidapplication.viewModel.TutorialActivity
import com.example.myandroidapplication.viewModel.UserAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class ChatList : AppCompatActivity() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userSearchView: SearchView
    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()

        userList = ArrayList()
        adapter = UserAdapter(this, userList)

        userRecyclerView = findViewById(R.id.userRecyclerView)
        //Istanziato per SearchView
        userSearchView = findViewById(R.id.userSearchView)

        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = adapter

        userSearchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })

        mDbRef.child("user").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                userList.clear()
                for (postSnapshot in snapshot.children){
                    val currentUser =
                        postSnapshot.getValue(User::class.java)

                    /*Questo if mostra tutti gli utenti eccetto quello che
                    ha effettuato il login
                     */
                    if (mAuth.currentUser?.uid != currentUser?.uid){
                        userList.add(currentUser!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {            }

        })
    }

    // Metodo aggiunto per SearchView
    private fun filterList(query: String?){

        if (query != null){
            val filteredList = ArrayList<User>()
            for (i in userList){
                if ((i.name!!.lowercase(Locale.ROOT).contains(query))||
                    (i.tag!!.lowercase(Locale.ROOT).contains(query)))
                    filteredList.add(i)
            }

            if (filteredList.isEmpty()){
                Toast.makeText(this, "Nessun utente trovato",
                    Toast.LENGTH_SHORT).show()
            } else {
                adapter.setfiltereList(filteredList)
            }
        }
    }

    //metodo per "iniettare" il menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
//        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //per effettuare il logout
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.home -> {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
//                finish()
                return true
            }
            R.id.settings -> {
                startActivity(Intent(applicationContext, SettingsActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
//                finish()
                return true
            }
            R.id.logout -> {
                //Logica per il logout
                mAuth.signOut()
                val intent = Intent(this@ChatList,
                    Login::class.java)
                finish()
                startActivity(intent)
                return true
            }
        }
//        if (item.itemId == R.id.logout){
//            //Logica per il logout
//            mAuth.signOut()
//            val intent = Intent(this@ChatList,
//                Login::class.java)
//            finish()
//            startActivity(intent)
//            return true
//        }
        return true
    }
}