package com.example.recipebook.recipelist

import android.view.KeyEvent
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.FrameLayout
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.pressKey
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.hasFocus
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isFocused
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.recipebook.R
import com.example.recipebook.core.AbstractButton
import com.example.recipebook.recipelist.matchers.GreaterOrEqualLengthMatcher
import com.example.recipebook.recipelist.matchers.HasNoEndIconMatcher
import com.example.recipebook.recipelist.matchers.HasVisibleEndIconMatcher
import com.example.recipebook.recipelist.matchers.LessThanLengthMatcher
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anything
import org.hamcrest.Matchers.not

class InputUi(
    containerIdMatcher: Matcher<View> = withParent(withId(R.id.inputView)),
    classTypeMatcher: Matcher<View> = withParent(isAssignableFrom(FrameLayout::class.java))
) : AbstractButton(
    interaction = onView(
        allOf(
            containerIdMatcher,
            classTypeMatcher,
            withId(R.id.inputLayout),
            isAssignableFrom(TextInputLayout::class.java)
        )
    )
) {
    private val inputInteraction: ViewInteraction = onView(
        allOf(
            withId(R.id.inputEditText),
            isAssignableFrom(TextInputEditText::class.java)
        )
    )

    private val autoCompleteInteraction: ViewInteraction = onView(
        allOf(
            withId(R.id.inputEditText),
            isAssignableFrom(AutoCompleteTextView::class.java)
        )
    )

    fun addInput(text: String) {
        inputInteraction.perform(ViewActions.typeText(text), ViewActions.closeSoftKeyboard())
    }

    fun clickClear() {
        interaction.perform(ClickEndIconAction())
    }

    fun clickFirstVariant() {
        autoCompleteInteraction.perform(ViewActions.click())

        onData(anything())
            .inRoot(RootMatchers.isPlatformPopup())
            .atPosition(0)
            .perform(ViewActions.click())
    }

    fun removeLetters(numberOfLetters: Int) {
        click()
        deleteLetters(numberOfLetters)
        closeKeyboard()
    }

    fun assertInitialState() {
        inputInteraction
            .check(matches(hasDescendant(withHint(R.string.enter_recipe))))
            .check(matches(withText("")))
        interaction.check(matches(HasNoEndIconMatcher()))
        assertDropdownHidden()
    }

    fun assertInputInsufficientUnfocusedState() {
        inputInteraction
            .check(matches(not(hasFocus())))
            .check(matches(withText(LessThanLengthMatcher(3))))
        interaction.check(matches(HasVisibleEndIconMatcher()))
        assertDropdownHidden()
    }

    fun assertInputInsufficientFocusedState() {
        inputInteraction
            .check(matches(hasFocus()))
            .check(matches(withText(LessThanLengthMatcher(3))))
        interaction.check(matches(HasVisibleEndIconMatcher()))
        assertDropdownHidden()
    }

    fun assertInputSufficientFocusedState() {
        inputInteraction
            .check(matches(hasFocus()))
            .check(matches(withText(GreaterOrEqualLengthMatcher(3))))
        interaction.check(matches(HasVisibleEndIconMatcher()))
        assertDropdownVisible()
    }

    fun assertInputSufficientUnfocusedState() {
        inputInteraction
            .check(matches(not(hasFocus())))
            .check(matches(withText(GreaterOrEqualLengthMatcher(3))))
        interaction.check(matches(HasVisibleEndIconMatcher()))
        assertDropdownHidden()
    }

    private fun deleteLetters(numberOfLetters: Int) {
        repeat(numberOfLetters) {
            inputInteraction.perform(pressKey(KeyEvent.KEYCODE_DEL))
        }
    }

    private fun assertDropdownVisible() {
        autoCompleteInteraction.check(matches(isFocused()))
        onView(withText("Your first variant text"))
            .inRoot(RootMatchers.isPlatformPopup())
            .check(matches(isDisplayed()))
    }

    private fun assertDropdownHidden() {
        autoCompleteInteraction.check(matches(not(hasFocus())))
        onView(withText("Your first variant text"))
            .inRoot(RootMatchers.isPlatformPopup())
            .check(doesNotExist())
    }

    private fun closeKeyboard() {
        inputInteraction.perform(ViewActions.closeSoftKeyboard())
    }
}