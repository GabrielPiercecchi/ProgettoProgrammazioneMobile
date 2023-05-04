package com.example.myandroidapplication.viewModel

import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myandroidapplication.R


class RatingActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super .onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate)

        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val button = findViewById<Button>(R.id.buttonRate)
        val ratingScale = findViewById<TextView>(R.id.ratingText)

        ratingBar.setOnRatingBarChangeListener { ratingBar, fl, b ->
            ratingScale.text = fl.toString()
            when (ratingBar.rating.toInt()){
                1 -> ratingScale.text = "Very Bad"
                2 -> ratingScale.text = "Bad"
                3 -> ratingScale.text = "Good"
                4 -> ratingScale.text = "Great"
                5 -> ratingScale.text = "Awesome"
                else -> ratingScale.text = " "
            }
        }

        button.setOnClickListener {
            val message = ratingBar.rating.toString()
            Toast.makeText(this@RatingActivity, "Rating is: "+message, Toast.LENGTH_SHORT).show()

        }
    }
}