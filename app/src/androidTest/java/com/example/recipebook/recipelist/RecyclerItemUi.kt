package com.example.recipebook.recipelist

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.recipebook.recipelist.matchers.RecyclerItemViewMatcher

class RecyclerItemUi(
    private val recyclerViewId: Int,
    private val position: Int
) {

    fun toggleLike(): SelectedUi =
        SelectedUi(
            onView(
                RecyclerItemViewMatcher(
                    recyclerViewId,
                    position,
                    R.id.like_button
                )
            )
        )

    fun clickItem() {
        onView(withId(recyclerViewId))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    position,
                    click()
                )
            )
    }
}