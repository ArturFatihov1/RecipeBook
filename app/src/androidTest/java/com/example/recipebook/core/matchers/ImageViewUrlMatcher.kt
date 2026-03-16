package com.example.recipebook.core.matchers

import android.view.View
import android.widget.ImageView
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.Description
import org.hamcrest.Matcher

class ImageViewUrlMatcher(private val expectedUrl: String) :
    BoundedMatcher<View, ImageView>(ImageView::class.java) {

    constructor(urlResId: Int) : this(
        InstrumentationRegistry.getInstrumentation()
            .targetContext
            .resources
            .getString(urlResId)
    )

    override fun describeTo(description: Description) {
        description.appendText("ImageView with URL: $expectedUrl")
    }

    override fun matchesSafely(item: ImageView): Boolean {
        return expectedUrl == item.tag
    }
}


fun hasDrawable(resId: Int): Matcher<View> {
    return object : BoundedMatcher<View, ImageView>(ImageView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has drawable: $resId")
        }

        override fun matchesSafely(item: ImageView): Boolean {
            val drawable = item.drawable ?: return false
            val expectedDrawable = item.context.getDrawable(resId)
            return drawable.constantState == expectedDrawable?.constantState
        }
    }
}