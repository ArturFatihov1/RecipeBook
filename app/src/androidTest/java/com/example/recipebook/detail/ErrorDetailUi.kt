package com.example.recipebook.detail

import android.view.View
import android.widget.ImageView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.recipebook.core.AbstractVisibilityImage
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

class ErrorDetailUi(
    id: Int,
    containerIdMatcher: Matcher<View>,
    classTypeMatcher: Matcher<View>
) : AbstractVisibilityImage(
    interaction = onView(
        allOf(
            withId(id),
            isAssignableFrom(ImageView::class.java),
            containerIdMatcher,
            classTypeMatcher
        )
    ),
    url = "https://www.themealdb.com/images/errorIngredient"
)