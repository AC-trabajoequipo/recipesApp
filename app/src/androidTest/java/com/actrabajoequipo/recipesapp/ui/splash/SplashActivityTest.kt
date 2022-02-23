package com.actrabajoequipo.recipesapp.ui.splash

import android.support.test.rule.ActivityTestRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.actrabajoequipo.recipesapp.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SplashActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

    @Test
    fun afterTheSplashGoToHome() {
        onView(withId(R.id.animation_view)).check(matches(isDisplayed()))
        Thread.sleep(6000)
        onView(withId(R.id.recycler)).check(matches(isDisplayed()))
    }
}