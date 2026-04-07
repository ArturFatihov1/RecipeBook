package com.example.recipebook.core

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.example.recipebook.core.matchers.ImageViewUrlMatcher
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

class ImageUI(
    id: Int,
    url: String,
    containerIdMatcher: Matcher<View>,
    classTypeMatcher: Matcher<View>
) : AbstractUi(
    interaction = onView(
        allOf(
            withId(id),
            isAssignableFrom(ImageView::class.java),
            containerIdMatcher,
            classTypeMatcher,
            withParent(isAssignableFrom(FrameLayout::class.java)),
            ImageViewUrlMatcher(url)
        )
    )
)