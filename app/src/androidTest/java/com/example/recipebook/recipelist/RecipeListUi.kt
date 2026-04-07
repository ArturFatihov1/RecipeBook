package com.example.recipebook.recipelist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
import androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.util.HumanReadables
import com.example.recipebook.R
import com.example.recipebook.Recipe
import com.example.recipebook.base.AbstractUi
import com.example.recipebook.core.matchers.hasItemCount
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import java.util.concurrent.TimeoutException

class RecipeListUi(
    items: List<Recipe>,
    classTypeMatcher: Matcher<View>,
    containerIdMatcher: Matcher<View>,
) : AbstractUi(
    interaction = onView(
        allOf(
            containerIdMatcher,
            classTypeMatcher,
            withId(R.id.recipeListLayout),
            isAssignableFrom(RecyclerView::class.java)
        )
    )
) {
    private val refreshLayoutInteraction = onView(
        allOf(
            containerIdMatcher,
            classTypeMatcher,
            withId(R.id.swipeRefreshLayout),
            isAssignableFrom(SwipeRefreshLayout::class.java)
        )
    )
    private var previousList = items
    private val recipes = items.toMutableList()

    fun addRecipes(newRecipes: List<Recipe>) {
        previousList = recipes.toList()
        recipes.addAll(newRecipes)
    }

    fun clickFirstRecipe() {
        item(0).clickItem()
    }

    fun toggleLikeRecipe(position: Int) {
        item(position).toggleLike().click()
    }

    fun refreshRecipes() {
        interaction.perform(scrollToPosition<RecyclerView.ViewHolder>(0))
        refreshLayoutInteraction.perform(swipeDown())
    }

    fun scrollToNextPage() {
        interaction.perform(scrollToPosition<RecyclerView.ViewHolder>(recipes.size - 1))
    }

    fun waitForRecipesListUpdate() {
        waitForRefreshingState()
    }

    fun assertRecipeListState() {
        interaction.check(matches(hasMinimumChildCount(1)))
    }

    fun assertRefreshLoadingState() {
        refreshLayoutInteraction.check { view, _ ->
            val swipe = view as? SwipeRefreshLayout ?: throw AssertionError("Not a SwipeRefreshLayout")
            if (!swipe.isRefreshing) throw AssertionError("SwipeRefreshLayout is not refreshing")
        }
    }

    fun assertNextPageLoadingState() {
        onView(withId(R.id.next_page_loading))
            .check(matches(isDisplayed()))
    }

    fun assertRecipeListChanged() {
        interaction.check(matches(hasItemCount(previousList.size)))
    }

    fun assertRecipeEmptyListState() {
        interaction.check(matches(hasChildCount(0)))
    }

    fun assertRecipeIsLiked(position: Int) {
        interaction.perform(scrollToPosition<RecipeViewHolder>(position))
        item(position).toggleLike().assertSelected()
    }

    fun assertRecipeIsUnLiked(position: Int) {
        interaction.perform(scrollToPosition<RecipeViewHolder>(position))
        item(position).toggleLike().assertNotSelected()
    }

    fun assertFavoritesCount(vararg positions: Int) {
        positions.forEach { position ->
            assertRecipeIsLiked(position)
        }
    }

    private fun item(position: Int): RecyclerItemUi = RecyclerItemUi(R.id.recipeListLayout, position)

    private fun waitForRefreshingState(isRefreshingExpected: Boolean = true, timeout: Long = 4000): ViewAction {
        return object : ViewAction {

            override fun getConstraints(): Matcher<View> = isRoot()

            override fun getDescription(): String =
                "Wait for SwipeRefreshLayout isRefreshing = $isRefreshingExpected"

            override fun perform(uiController: UiController, rootView: View) {
                val start = System.currentTimeMillis()
                val end = start + timeout

                do {
                    val swipe = rootView.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)

                    if (swipe != null && swipe.isRefreshing == isRefreshingExpected) {
                        return
                    }

                    uiController.loopMainThreadForAtLeast(50)
                } while (System.currentTimeMillis() < end)

                throw PerformException.Builder()
                    .withCause(TimeoutException())
                    .withActionDescription(description)
                    .withViewDescription(HumanReadables.describe(rootView))
                    .build()
            }
        }
    }
}