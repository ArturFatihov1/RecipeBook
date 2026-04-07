package com.example.recipebook.recipelist.matchers

import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class GreaterOrEqualLengthMatcher(
    private val min: Int
) : TypeSafeMatcher<String>() {

    override fun describeTo(description: Description) {
        description.appendText("length >= $min")
    }

    override fun matchesSafely(item: String): Boolean = item.length >= min
}