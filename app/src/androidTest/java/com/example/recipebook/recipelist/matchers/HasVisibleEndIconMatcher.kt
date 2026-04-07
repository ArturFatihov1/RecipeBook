package com.example.recipebook.recipelist.matchers

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description

class HasVisibleEndIconMatcher : BoundedMatcher<View, TextInputLayout>(TextInputLayout::class.java) {

    override fun describeTo(description: Description) {
        description.appendText("TextInputLayout has visible end icon")
    }

    override fun matchesSafely(view: TextInputLayout): Boolean = view.isEndIconVisible
}