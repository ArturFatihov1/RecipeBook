package com.example.recipebook.core

import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.allOf

class TitleUi(
    viewId: Int,
    parentId: Int,
    containerId: Int,
    text: String,
) : AbstractUi(
    interaction = onView(
        allOf(
            withId(viewId),
            withText(text),
            isAssignableFrom(AppCompatTextView::class.java),
            withParent(withId(parentId)),
            withParent(isAssignableFrom(LinearLayout::class.java)),
            isDescendantOfA(withId(containerId)),
            isDescendantOfA(isAssignableFrom(LinearLayout::class.java)),
        )
    )
)