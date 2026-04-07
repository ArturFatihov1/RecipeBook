package com.example.recipebook.recipelist.matchers

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class RecyclerItemViewMatcher(
    private val recyclerViewId: Int,
    private val position: Int,
    private val targetViewId: Int
) : TypeSafeMatcher<View>() {

    override fun describeTo(description: Description) {
        description.appendText(
            "View with id $targetViewId at position $position in RecyclerView $recyclerViewId"
        )
    }

    override fun matchesSafely(view: View): Boolean {
        val recyclerView = view.rootView.findViewById<RecyclerView>(recyclerViewId) ?: return false
        val viewHolder = recyclerView.findViewHolderForAdapterPosition(position) ?: return false
        val targetView = viewHolder.itemView.findViewById<View>(targetViewId)
        return view === targetView
    }
}