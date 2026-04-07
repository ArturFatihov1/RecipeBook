package com.example.recipebook.base

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions

abstract class AbstractButton(
    interaction: ViewInteraction
) : AbstractUi(interaction) {
    fun click() {
        interaction.perform(ViewActions.click())
    }
}