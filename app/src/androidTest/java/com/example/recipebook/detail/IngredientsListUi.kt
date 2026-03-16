package com.example.recipebook.detail

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.recipebook.Ingredient
import com.example.recipebook.R
import com.example.recipebook.core.AbstractVisibility
import com.example.recipebook.core.ProgressUi
import com.example.recipebook.core.matchers.hasItemCount
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

class IngredientsListUi(
    private val id: Int,
    private val listIngredient: List<Ingredient>,
    ingredientId: Int,
    containerIdMatcher: Matcher<View>,
    classTypeMatcher: Matcher<View>
) : AbstractVisibility(
    interaction = onView(
        allOf(
            withId(id),
            isAssignableFrom(RecyclerView::class.java),
            containerIdMatcher,
            classTypeMatcher
        )
    )
) {
    private val progressUi = ProgressUi(
        id = R.id.ivIngredient,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = withParent(withId(ingredientId))
    )
    private val errorUi = ErrorDetailUi(
        id = R.id.ivIngredient,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = withParent(withId(ingredientId))
    )
    private val successUi = SuccessDetailUi(
        id = R.id.ivIngredient,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = withParent(withId(ingredientId))
    )

    fun assertProgressUi() {
        assertIngredientsDisplayed()
        progressUi.assertVisible()
        errorUi.assertNotVisibleImage()
        successUi.assertNotVisibleImage()
    }

    fun assertErrorUi() {
        assertIngredientsDisplayed()
        progressUi.assertNotVisible()
        errorUi.assertVisibleImage()
        successUi.assertNotVisibleImage()
    }

    fun assertSuccessUi() {
        assertIngredientsDisplayed()
        progressUi.assertNotVisible()
        errorUi.assertNotVisibleImage()
        successUi.assertVisibleImage()
    }

    fun waitTillVisible() {
        progressUi.waitTillVisibleProgress()
    }

    fun assertIngredientsDisplayed() {
        listIngredient.forEach { ingredient ->
            onView(withId(id)).check(matches(hasDescendant(withText(ingredient.name))))
            onView(withId(id)).check(matches(hasDescendant(withText(ingredient.measure))))
        }
        onView(withId(id)).check(matches(hasItemCount(listIngredient.size)))
    }
}