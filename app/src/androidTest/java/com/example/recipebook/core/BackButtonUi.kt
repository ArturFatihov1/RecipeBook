package com.example.recipebook.core

import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import org.hamcrest.CoreMatchers.allOf

class BackButtonUi(
    viewId: Int,
    parentId: Int,
    containerId: Int,
) : AbstractButton(
    interaction = onView(
        allOf(
            withId(viewId),
            withParent(withId(parentId)),
            withParent(isAssignableFrom(LinearLayout::class.java)),
            isAssignableFrom(ImageButton::class.java),
            isDescendantOfA(withId(containerId)),
            isDescendantOfA(isAssignableFrom(LinearLayout::class.java)),
        )
    )
)