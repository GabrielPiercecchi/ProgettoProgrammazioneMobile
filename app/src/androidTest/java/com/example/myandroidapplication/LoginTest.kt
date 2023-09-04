package com.example.myandroidapplication

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.myandroidapplication.view.Login
import org.junit.Rule
import org.junit.Test

class LoginEspressoTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(Login::class.java)

    @Test
    fun testLoginWithValidCredentials() {
        val email = "francesco@gmail.com"
        val password = "FrancescoP"

        // Insert email and password
        onView(withId(R.id.edit_email)).perform(typeText(email))
        onView(withId(R.id.edit_password)).perform(typeText(password))

        // Click the login button
        onView(withId(R.id.btnLogin)).perform(click())

        // Sleep for a short while to allow time for the login process to complete
        Thread.sleep(2000)

        onView(withId(R.id.bottomNavigationView)).check(matches(isDisplayed()))
    }

    @Test
    fun testLoginFailure() {
        val email = "invalid@example.com"
        val password = "invalidPassword"

        // Insert email and password
        onView(withId(R.id.edit_email)).perform(typeText(email))
        onView(withId(R.id.edit_password)).perform(typeText(password))

        // Click the login button
        onView(withId(R.id.btnLogin)).perform(click())

        // Add assertions here to verify the expected behavior
        // For example, you could use onView(withId(R.id.txtError)).check(matches(isDisplayed()))
    }
}