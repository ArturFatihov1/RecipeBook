package com.example.recipebook.recipelist

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.recipebook.core.AbstractUi
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class LoadingDialogUi(
    containerIdMatcher: Matcher<View>,
    classTypeMatcher: Matcher<View>
) : AbstractUi(
    interaction = onView(
        allOf(
            containerIdMatcher,
            classTypeMatcher,
            withId(R.id.loadingDialogView),
            hasDescendant(
                allOf(
                    withId(R.id.loadingTitle),
                    withText(R.string.loadingTitle),
                    isAssignableFrom(TextView::class.java)
                )
            ),

            hasDescendant(
                allOf(
                    withId(R.id.loadingBody),
                    withText(R.string.loadingBody),
                    isAssignableFrom(TextView::class.java)
                )
            ),

            hasDescendant(isAssignableFrom(ProgressBar::class.java))
        )
    ).inRoot(isDialog())
)