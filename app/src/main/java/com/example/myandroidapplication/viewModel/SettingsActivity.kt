package com.example.myandroidapplication.viewModel

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.myandroidapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            loadSettings()
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
        }

        private fun loadSettings(){
            val sp = PreferenceManager.getDefaultSharedPreferences(requireContext())
            /**
             * TODO: aggiungere la parte delle notifiche
             */
            //val receiveNotifications = sp.getBoolean("receive_notifications", false)

            sp.registerOnSharedPreferenceChangeListener{ _, key ->
                when(key){
                    "theme" -> {
                        val theme = sp.getString("theme", "")
                        if (theme != null) changeTheme(theme)
                    }
                    "bnv" -> {
                        val bnv = sp.getString("bnv", "")
                        if (bnv != null) bnvPosition(bnv)
                    }
                }
            }
        }

        private fun bnvPosition(position: String){
            val bottomNavigationView = requireView().findViewById<BottomNavigationView>(R.id.bottomNavigationView)

            when (position) {
                "center" -> {
                    val params = bottomNavigationView.layoutParams as FrameLayout.LayoutParams
                    params.gravity = Gravity.CENTER_HORIZONTAL // Centro orizzontale
                    bottomNavigationView.layoutParams = params
                }
                "left" -> {
                    val params = bottomNavigationView.layoutParams as FrameLayout.LayoutParams
                    params.gravity = Gravity.START // Sinistra
                    bottomNavigationView.layoutParams = params

                }
                "right" -> {
                    val params = bottomNavigationView.layoutParams as FrameLayout.LayoutParams
                    params.gravity = Gravity.END // Destra
                    bottomNavigationView.layoutParams = params
                }
            }
        }
        private fun changeTheme(themePreference: String){
            when (themePreference) {
                "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                "system" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
            true
        }
    }
}