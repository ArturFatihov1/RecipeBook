package com.example.recipebook.base

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.hamcrest.Matchers.not

abstract class AbstractUi(
    protected val interaction: ViewInteraction
) {
    open fun assertVisible() {
        interaction.check(matches(isDisplayed()))
    }

    fun assertNotVisible() {
        interaction.check(matches(not(isDisplayed())))
    }

    fun asserDoesNotExist() {
        interaction.check(ViewAssertions.doesNotExist())
    }
}