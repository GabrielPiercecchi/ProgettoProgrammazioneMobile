package com.example.myandroidapplication.viewModel

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.example.myandroidapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadSettings()
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
                    restartApp()
                }
                "bnv" -> {
                    val bnv = sp.getString("bnv", "")
                    if (bnv != null) bnvPosition(bnv)
                    restartApp()
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
                else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        true
    }

    private fun restartApp() {
        val intent = context?.packageManager?.getLaunchIntentForPackage(requireContext().packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

}