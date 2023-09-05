package com.example.myandroidapplication

import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.myandroidapplication.view.MainActivity
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetPlayerTest {

    private var mockTest: Unit = mockk()
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    @Rule
    private val mainActivity = MainActivity()

    @Before
    fun setUp(){
        mockTest = mainActivity.getPlayer()
    }
    @Test
    @UiThreadTest
    fun testGetPlayer() {
        every { mainActivity.getPlayer() } returns mockTest
    }
}