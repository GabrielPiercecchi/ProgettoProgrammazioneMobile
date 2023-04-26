package com.example.myandroidapplication.viewModel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myandroidapplication.R
import hotchemi.android.rate.AppRate

class ActivityRate : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate)

        AppRate.with(this)
            .setInstallDays(1)
            .setLaunchTimes(1)
            .setRemindInterval(2)
            .monitor()
        AppRate.showRateDialogIfMeetsConditions(this)
    }
}