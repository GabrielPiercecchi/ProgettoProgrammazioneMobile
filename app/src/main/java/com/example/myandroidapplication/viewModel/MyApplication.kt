package com.example.myandroidapplication.viewModel

import android.app.Application

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        //loadTheme()
    }

    /*private fun loadTheme() {
        val themePreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE)
        val nightMODE = themePreferences.getString("mode", "light")
        val editor: SharedPreferences.Editor
        when (nightMODE) {
            "light" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor = themePreferences.edit()
                editor.putString("mode", "light")
            }
            "dark" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor = themePreferences.edit()
                editor.putString("mode", "dark")
            }
            "system" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                editor = themePreferences.edit()
                editor.putString("mode", "system")
            }
            else -> {
                editor = themePreferences.edit()
            }
        }
        editor.apply()
    }*/
}