package com.example.recipebook.recipelist

import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.example.recipebook.R
import com.example.recipebook.base.AbstractButton
import org.hamcrest.Matchers.allOf

class FavoritesButtonUi(
    containerId: Int,
) : AbstractButton(
    interaction = onView(
        allOf(
            withId(R.id.recipeLike),
            isAssignableFrom(AppCompatButton::class.java),
            withParent(withId(R.id.favoriteAndSearchContainer)),
            withParent(isAssignableFrom(LinearLayout::class.java)),
            isDescendantOfA(withId(containerId)),
            isDescendantOfA(isAssignableFrom(LinearLayout::class.java)),
        )
    )
)