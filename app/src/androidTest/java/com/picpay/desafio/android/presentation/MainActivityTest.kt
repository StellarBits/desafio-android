package com.picpay.desafio.android.presentation

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import com.picpay.desafio.android.R
import org.junit.Test


class MainActivityTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun shouldDisplayTitle() {
        launchActivity<MainActivity>().apply {
            val expectedTitle = context.getString(R.string.title)

            moveToState(Lifecycle.State.RESUMED)

            onView(withText(expectedTitle)).check(matches(isDisplayed()))
        }
    }
}