package com.example.recipebook.detail

import android.view.View
import android.widget.ImageButton
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.example.recipebook.R
import com.example.recipebook.core.AbstractVisibility
import com.example.recipebook.core.matchers.hasDrawable
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

class RecipeLikeUi(
    id: Int,
    containerIdMatcher: Matcher<View>,
    classTypeMatcher: Matcher<View>
) : AbstractVisibility(
    interaction = onView(
        allOf(
            withId(id),
            isAssignableFrom(ImageButton::class.java),
            withParent(withId(R.id.detailHeader)),
            classTypeMatcher
        )
    )
) {
    fun click() {
        interaction.perform(ViewActions.click())
    }

    fun isLiked() {
        interaction.check(matches(hasDrawable(R.drawable.ic_like_selected)))
    }

    fun isNotLiked() {
        interaction.check(matches(hasDrawable(R.drawable.ic_like_unselected)))
    }
}
