package com.ricwil.habit

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// Once app is split into MVVM model using ViewModels and Adapters, testing will be done with each individual fragment

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class AppTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun happyPath() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.delete_tasks_button)).perform(click())
        onView(withId(R.id.new_task_button)).perform(click())
        onView(withId(R.id.create_new_task_instruction)).check(matches(isDisplayed()))
        onView(withId(R.id.create_new_task_name))
            .perform(click())
            .perform(typeText("New Task"))
        closeSoftKeyboard()
        onView(withId(R.id.create_task_button)).perform(click())
        onView(withId(R.id.task_name)).check(matches(withText("New Task")))
    }

}