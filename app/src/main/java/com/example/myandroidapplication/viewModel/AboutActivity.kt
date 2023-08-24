package com.example.myandroidapplication.viewModel

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myandroidapplication.R

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val gabrielLink: TextView = findViewById(R.id.gabriel)
        gabrielLink.movementMethod = LinkMovementMethod.getInstance()

        val toscaLink: TextView = findViewById(R.id.tosca)
        toscaLink.movementMethod = LinkMovementMethod.getInstance()

        val lorenzoLink: TextView = findViewById(R.id.lorenzo)
        lorenzoLink.movementMethod = LinkMovementMethod.getInstance()

    }
}