package com.example.recipebook.recipelist

import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.recipebook.R
import com.example.recipebook.core.AbstractUi
import org.hamcrest.Matchers.allOf

class LoadingDialogUi : AbstractUi(
    interaction = onView(
        allOf(
            withId(R.id.loadingDialogView),
            isAssignableFrom(ConstraintLayout::class.java),
            hasDescendant(
                allOf(
                    withId(R.id.loadingTitle),
                    withText(R.string.loading_title),
                    isAssignableFrom(TextView::class.java)
                )
            ),

            hasDescendant(
                allOf(
                    withId(R.id.loadingBody),
                    withText(R.string.loading_body),
                    isAssignableFrom(TextView::class.java)
                )
            ),

            hasDescendant(
                allOf(
                    withId(R.id.loadingProgress),
                    isAssignableFrom(ProgressBar::class.java)
                )
            )
        )
    ).inRoot(isDialog())
)