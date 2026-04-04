package com.example.recipebook.core

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.recipebook.R
import com.example.recipebook.core.matchers.clickItemAtPosition
import com.example.recipebook.core.matchers.hasItemCount
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

class ScrollableListUi(
    id: Int,
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

    fun assertCount(expectedCount: Int) {
        interaction.check(matches(hasItemCount(expectedCount)))
    }

    fun assertFavoritesEmptyState() {
        assertFavoriteListEmpty()
    }

    fun assertRecipeEmptyListState() {
        assertFavoriteListEmpty()
    }

    private fun assertFavoriteListEmpty() {
        assertCount(0)
        onView(withText(R.string.empty_favorites)).check(matches(isDisplayed()))
    }

    fun clickFirstRecipe() {
        interaction.perform(clickItemAtPosition(position = 0))
    }

    fun clickUnLike(recipeId: Int) {
        interaction.perform(
            clickItemAtPosition(position = recipeId, childId = R.id.recipeLike)
        )
    }
}
