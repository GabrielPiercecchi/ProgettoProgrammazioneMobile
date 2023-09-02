package com.example.myandroidapplication.view

import android.content.DialogInterface
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myandroidapplication.R
import com.example.myandroidapplication.viewModel.util.NetworkUtils

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        if (!NetworkUtils.isInternetAvailable(this)) {
            NetworkUtils.showNoInternetDialog(this)
        }

        val gabrielLink: TextView = findViewById(R.id.gabriel)
        gabrielLink.movementMethod = LinkMovementMethod.getInstance()

        val toscaLink: TextView = findViewById(R.id.tosca)
        toscaLink.movementMethod = LinkMovementMethod.getInstance()

        val lorenzoLink: TextView = findViewById(R.id.lorenzo)
        lorenzoLink.movementMethod = LinkMovementMethod.getInstance()

    }
}