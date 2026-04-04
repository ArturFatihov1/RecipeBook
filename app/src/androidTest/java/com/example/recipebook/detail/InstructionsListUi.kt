package com.example.recipebook.detail

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.recipebook.core.AbstractUi
import com.example.recipebook.core.matchers.hasItemCount
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

class InstructionsListUi(
    private val id: Int,
    private val instructions: List<String>,
    containerIdMatcher: Matcher<View>,
    classTypeMatcher: Matcher<View>
) : AbstractUi(
    interaction = onView(
        allOf(
            withId(id),
            isAssignableFrom(RecyclerView::class.java),
            containerIdMatcher,
            classTypeMatcher
        )
    )
) {
    fun assertInstructionsTextVisible() {
        instructions.forEach { stepText ->
            onView(withId(id)).check(matches(hasDescendant(withText(stepText))))
        }
        onView(withId(id)).check(matches(hasItemCount(instructions.size)))
    }
}

