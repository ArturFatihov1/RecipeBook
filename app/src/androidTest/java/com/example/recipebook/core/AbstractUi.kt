package com.example.recipebook.core

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matchers

abstract class AbstractUi(
    protected val interaction: ViewInteraction
) {
    open fun assertVisible() {
        interaction.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun assertNotVisible() {
        interaction.check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))
    }

    fun asserDoesNotExist() {
        interaction.check(ViewAssertions.doesNotExist())
    }
}