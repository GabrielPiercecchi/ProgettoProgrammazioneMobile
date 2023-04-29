package com.example.myandroidapplication.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.myandroidapplication.R
import com.example.myandroidapplication.viewModel.AboutActivity
import com.example.myandroidapplication.viewModel.HomeFragment
import com.example.myandroidapplication.viewModel.SettingsActivity
import com.example.myandroidapplication.viewModel.TutorialActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var bottomNavigationView: BottomNavigationView

    // Variabile utilizzata per le autenticazioni Firebase
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()

        //navigation drawer
        drawerLayout = findViewById(R.id.drawer_layout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }

        //bottom navigation bar
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_playersandchats -> {
                    startActivity(Intent(applicationContext, ChatList::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    true
                }
                R.id.bottom_leaderboards -> {
                    startActivity(Intent(applicationContext, LeaderboardsActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    true
                }
                else -> false
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_settings -> {
                startActivity(Intent(applicationContext, SettingsActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
//                finish()
                return true
            }
            R.id.nav_about -> {
                startActivity(Intent(applicationContext, AboutActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
//                finish()
                return true
            }
            R.id.nav_tutorial -> {
                startActivity(Intent(applicationContext, TutorialActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
//                finish()
                return true
            }
            R.id.nav_logout -> {
                //Logica per il logout
                mAuth.signOut()
                finish()
                return true
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    //Questa nella home può anche essere evitata a sto punto
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.settings_menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }


}

