package com.gzozulin.leds

import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun shouldShowGuess() {
        onView(withId(R.id.button_a)).perform(click())
        onView(withId(R.id.button_b)).perform(click())
        onView(withId(R.id.button_c)).perform(click())
        onView(withId(R.id.guess)).check(matches(withText(containsString("A, B, C"))))
    }

    @Test
    fun shouldShowAnswer() {
        val expected = activityRule.activity.viewModel.answer.joinToString().toUpperCase()
        onView(withId(R.id.answer)).check(matches(withText(containsString(expected))))
    }

    @Test
    fun shouldShowFirstNoneAsGrey() {
        onView(withId(R.id.led_3)).check { view, _ ->
            assertColor(view, R.color.dark_grey)
        }
    }

    @Test
    fun shouldShowSecondCorrectAsGreen() {
        activityRule.activity.viewModel.answer = listOf('a', 'b', 'c')
        onView(withId(R.id.button_a)).perform(click())
        onView(withId(R.id.button_b)).perform(click())
        onView(withId(R.id.led_3)).check { view, _ ->
            assertColor(view, R.color.green)
        }
    }

    @Test
    fun shouldShowSecondExistingAsOrange() {
        activityRule.activity.viewModel.answer = listOf('a', 'b', 'b')
        onView(withId(R.id.button_b)).perform(click())
        onView(withId(R.id.button_a)).perform(click())
        onView(withId(R.id.led_3)).check { view, _ ->
            assertColor(view, R.color.orange)
        }
    }

    @Test
    fun shouldShowSecondIncorrectAsRed() {
        activityRule.activity.viewModel.answer = listOf('a', 'a', 'a')
        onView(withId(R.id.button_c)).perform(click())
        onView(withId(R.id.button_c)).perform(click())
        onView(withId(R.id.led_2)).check { view, _ ->
            assertColor(view, R.color.red)
        }
    }

    private fun assertColor(view: View, @ColorInt color: Int) {
        val expected: ColorFilter = PorterDuffColorFilter(
            ContextCompat.getColor(activityRule.activity, color), PorterDuff.Mode.SRC_IN)
        assertThat(view.background.colorFilter, `is`(equalTo(expected)))
    }
}