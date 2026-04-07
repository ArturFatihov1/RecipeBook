package com.example.recipebook.recipelist

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.recipebook.core.AbstractUi
import com.example.recipebook.core.matchers.waitTillDisplayed
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class ErrorDialogUi(
    containerIdMatcher: Matcher<View>,
    classTypeMatcher: Matcher<View>
) : AbstractUi(
    interaction = onView(
        allOf(
            containerIdMatcher,
            classTypeMatcher,
            withId(R.id.errorDialogView),
            hasDescendant(
                allOf(
                    withId(R.id.loadingTitle),
                    withText(R.string.loadingTitle),
                    isAssignableFrom(TextView::class.java)
                )
            ),
            hasDescendant(
                allOf(
                    containerIdMatcher,
                    classTypeMatcher,
                    withId(R.id.errorBody),
                    isAssignableFrom(TextView::class.java)
                )
            ),
            hasDescendant(
                allOf(
                    withId(R.id.confirmErrorButton),
                    withText(R.string.ok),
                    isAssignableFrom(AppCompatButton::class.java)
                )
            )
        )
    ).inRoot(isDialog())
) {

    fun click() {
        onView(withId(R.id.confirmErrorButton))
            .inRoot(isDialog())
            .perform(ViewActions.click())
    }

    fun waitTillError() {
        onView(isRoot()).perform(waitTillDisplayed(R.id.errorDialogView, 4000))
    }
}