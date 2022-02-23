package com.actrabajoequipo.recipesapp.ui.home

import android.support.test.rule.ActivityTestRule
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.actrabajoequipo.recipesapp.MainActivity
import com.actrabajoequipo.recipesapp.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

const val FIRST_ITEM_TEXT = "Cocido madrileño"

@LargeTest
@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun theFirstElementOfRecyclerIsCocido() {
        val textView = onView(
            allOf(
                withId(R.id.name),
                withText(FIRST_ITEM_TEXT),
                withParent(withParent(withId(R.id.carViewContainer))),
                isDisplayed()
            )
        )
        textView.check(matches(withText(FIRST_ITEM_TEXT)))
    }

    @Test
    fun whenGoToDetailTheToolbarNameIsCorrect() {
        val textView = onView(
            allOf(
                withId(R.id.name), withText(FIRST_ITEM_TEXT),
                withParent(withParent(withId(R.id.carViewContainer))),
                isDisplayed()
            )
        )
        textView.check(matches(withText(FIRST_ITEM_TEXT)))

        val recyclerView = onView(
            allOf(
                withId(R.id.recycler),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        )
        recyclerView.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        Thread.sleep(1000)

        onView(
            allOf(
                withText(FIRST_ITEM_TEXT),
                isDisplayed()
            )
        )
        //onView(withId(R.id.appBarLayout)).perform(ViewActions.swipeUp())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    /*fun getText(matcher: ViewInteraction): String {
        var text = String()
        matcher.perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return "Text of the view"
            }

            override fun perform(uiController: UiController, view: View) {
                val tv = view as TextView
                text = tv.text.toString()
            }
        })

        return text
    }*/
}
