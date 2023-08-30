package com.example.myandroidapplication

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.myandroidapplication.viewModel.Login
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class LoginEspressoTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(Login::class.java)

    @Test
    fun testLoginSuccess() {
        val email = "gabriel@gmail.com"
        val password = "GabrielP"

        Espresso.onView(ViewMatchers.withId(R.id.edit_email))
            .perform(ViewActions.typeText(email))
        Espresso.onView(ViewMatchers.withId(R.id.edit_password))
            .perform(ViewActions.typeText(password), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.btnLogin))
            .perform(ViewActions.click())

        // Add assertions here to verify the expected behavior
    }

    @Test
    fun testLoginFailure() {
        val email = "invalid@example.com"
        val password = "invalidPassword"

        Espresso.onView(ViewMatchers.withId(R.id.edit_email))
            .perform(ViewActions.typeText(email))
        Espresso.onView(ViewMatchers.withId(R.id.edit_password))
            .perform(ViewActions.typeText(password), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.btnLogin))
            .perform(ViewActions.click())

        // Add assertions here to verify the expected behavior
    }
}
