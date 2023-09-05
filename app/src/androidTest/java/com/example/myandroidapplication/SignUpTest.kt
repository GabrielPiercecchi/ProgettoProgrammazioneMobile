package com.example.myandroidapplication

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.myandroidapplication.view.SignUp
import org.junit.Rule
import org.junit.Test

class SignUpTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(SignUp::class.java)

    @Test
    fun testSignUp() {
        val tag = "#1234567"
        val name = "TestUser"
        val email = "test@gmail.com"
        val password = "TestTest"

        // Insert credentials
        Espresso.onView(ViewMatchers.withId(R.id.edit_tag)).perform(ViewActions.typeText(tag))
        Espresso.onView(ViewMatchers.withId(R.id.edit_name)).perform(ViewActions.typeText(name))
        Espresso.onView(ViewMatchers.withId(R.id.edit_email)).perform(ViewActions.typeText(email))
        Espresso.onView(ViewMatchers.withId(R.id.edit_password))
            .perform(ViewActions.typeText(password))
        Espresso.onView(ViewMatchers.withId(R.id.confirm_password))
            .perform(ViewActions.typeText(password))

        // Nasconde la tastiera per fare in modo che il tasto SignUp sia cliccabile
        Espresso.closeSoftKeyboard()
        Thread.sleep(2000)

        // Click the login button
        Espresso.onView(ViewMatchers.withId(R.id.btnSignUp)).perform(ViewActions.click())

        // Sleep for a short while to allow time for the login process to complete
        Thread.sleep(2000)

        Espresso.onView(ViewMatchers.withId(R.id.bottomNavigationView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}