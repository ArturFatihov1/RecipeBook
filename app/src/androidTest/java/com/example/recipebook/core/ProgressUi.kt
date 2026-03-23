package com.example.recipebook.core

import android.view.View
import android.widget.ProgressBar
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.recipebook.core.matchers.waitTillDisplayed
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

class ProgressUi(
    private val id: Int,
    containerIdMatcher: Matcher<View>,
    classTypeMatcher: Matcher<View>,
) : AbstractVisibility(
    interaction = onView(
        allOf(
            withId(id),
            isAssignableFrom(ProgressBar::class.java),
            containerIdMatcher,
            classTypeMatcher
        )
    )
) {
    fun waitTillVisibleProgress() {
        onView(isRoot()).perform(waitTillDisplayed(id, 1500))
    }
}