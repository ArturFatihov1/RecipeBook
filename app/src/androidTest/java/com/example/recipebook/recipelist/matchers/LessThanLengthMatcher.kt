package com.example.recipebook.recipelist.matchers

import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class LessThanLengthMatcher(
    private val max: Int
) : TypeSafeMatcher<String>() {

    override fun describeTo(description: Description) {
        description.appendText("length < $max")
    }

    override fun matchesSafely(item: String): Boolean = item.length < max
}