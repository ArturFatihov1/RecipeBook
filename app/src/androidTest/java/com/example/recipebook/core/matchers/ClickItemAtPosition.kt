package com.example.recipebook.core.matchers

import android.os.SystemClock
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import org.hamcrest.Matcher

private const val ATTACH_TIMEOUT_MS = 2_000L
private const val POLL_MS = 50L

/**
 * Клик по ячейке [RecyclerView] по [adapter position][RecyclerView.Adapter].
 *
 * @param position индекс в адаптере (как в тестах 0, 1, …)
 * @param childId если задан — клик по дочернему view внутри itemView иначе по всей ячейке
 */
fun clickItemAtPosition(position: Int, childId: Int? = null): ViewAction {
    require(position >= 0) { "position must be >= 0, was $position" }

    return object : ViewAction {
        override fun getConstraints(): Matcher<View> =
            isAssignableFrom(RecyclerView::class.java)

        override fun getDescription(): String = buildString {
            append("click RecyclerView item at adapter position ")
            append(position)
            if (childId != null) {
                append(" (child id=")
                append(childId)
                append(')')
            }
        }

        override fun perform(uiController: UiController, view: View) {
            val recyclerView = view as RecyclerView
            recyclerView.scrollToPosition(position)
            uiController.loopMainThreadUntilIdle()

            val itemView = waitForItemView(uiController, recyclerView, position)
            resolveClickTarget(itemView, position, childId).performClick()
        }
    }
}

private fun waitForItemView(
    uiController: UiController,
    recyclerView: RecyclerView,
    position: Int,
): View {
    val deadline = SystemClock.uptimeMillis() + ATTACH_TIMEOUT_MS
    while (true) {
        recyclerView.findViewHolderForAdapterPosition(position)
            ?.itemView
            ?.takeIf { it.isAttachedToWindow }
            ?.let { return it }

        if (SystemClock.uptimeMillis() >= deadline) {
            error(
                "RecyclerView: item at adapter position $position did not attach within " +
                    "${ATTACH_TIMEOUT_MS}ms after scrollToPosition (adapter itemCount=" +
                    "${recyclerView.adapter?.itemCount ?: "null"})"
            )
        }
        uiController.loopMainThreadForAtLeast(POLL_MS)
    }
}

private fun resolveClickTarget(itemView: View, position: Int, childId: Int?): View {
    if (childId == null) return itemView
    return itemView.findViewById(childId)
        ?: error("Item at adapter position $position: no child with id $childId")
}
