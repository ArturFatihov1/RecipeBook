package com.example.recipebook.recipelist

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Matcher

class ClickEndIconAction : ViewAction {
    override fun getConstraints(): Matcher<View> {
        return isAssignableFrom(TextInputLayout::class.java)
    }

    override fun getDescription(): String {
        return "Click on TextInputLayout end icon"
    }

    override fun perform(uiController: UiController, view: View) {
        val layout = view as TextInputLayout

        val endIcon = layout.findViewById<View>(
            com.google.android.material.R.id.text_input_end_icon
        ) ?: throw AssertionError("End icon not found")

        if (!endIcon.isShown) throw AssertionError("End icon is not visible")

        endIcon.performClick()
    }
}