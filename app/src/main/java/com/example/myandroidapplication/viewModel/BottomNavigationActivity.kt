package com.example.myandroidapplication.viewModel

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.myandroidapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationActivity: AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.userRecyclerView -> {
                val intent = Intent(this, ChatList::class.java)
                startActivity(intent)}
            R.id.bottom_leaderboards ->  {
                val intent = Intent(this, LeaderboardsActivity::class.java)
                startActivity(intent)}
        }
        return true
    }
}