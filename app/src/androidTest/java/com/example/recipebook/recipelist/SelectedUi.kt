package com.example.recipebook.recipelist

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isSelected
import com.example.recipebook.base.AbstractButton
import org.hamcrest.Matchers.not

class SelectedUi(
    interaction: ViewInteraction
) : AbstractButton(interaction) {

    fun assertSelected() {
        assertVisible()
        interaction.check(matches(isSelected()))
    }

    fun assertNotSelected() {
        assertVisible()
        interaction.check(matches(not(isSelected())))
    }
}