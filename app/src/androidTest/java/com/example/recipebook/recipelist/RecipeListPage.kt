package com.example.recipebook.recipelist

import android.view.View
import android.widget.LinearLayout
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.example.recipebook.R
import com.example.recipebook.Recipe
import org.hamcrest.Matcher

class RecipeListPage(recipes: List<Recipe>) {
    private val containerIdMatcher: Matcher<View> = withParent(withId(R.id.recipeListLayout))
    private val classTypeMatcher: Matcher<View> = withParent(isAssignableFrom(LinearLayout::class.java))

    private val favoriteButtonUi = ButtonUi(
        id = R.id.recipeLike,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )

    private val searchFieldUi = InputUi()

    private val recipeListUi = ScrollableListUi(
        items = recipes,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )

    private val loadingDialogUi = LoadingDialogUi(
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )

    private val errorDialogUi = ErrorDialogUi(
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )

    fun addRecipes(newRecipes: List<Recipe>) {
        recipeListUi.addRecipes(newRecipes)
    }

    fun clickFirstRecipe() {
        recipeListUi.clickFirstRecipe()
    }

    fun clickFavoriteButton() {
        favoriteButtonUi.click()
    }

    fun clickLikeOnRecipe(recipeId: Int) {
        recipeListUi.clickLikeOnRecipe(recipeId = recipeId)
    }

    fun refreshRecipes() {
        recipeListUi.refreshRecipes()
    }

    fun scrollToNextPage() {
        recipeListUi.scrollToNextPage()
    }

    fun clickUnLike(recipeId: Int) {
        recipeListUi.clickUnLike(recipeId = recipeId)
    }

    fun waitForRecipesListUpdate() {
        recipeListUi.waitForRecipesListUpdate()
    }

    fun assertRecipeListState() {
        recipeListUi.assertRecipeListState()
    }

    fun assertRefreshState() {
        recipeListUi.assertRefreshLoadingState()
    }

    fun assertNextPageState() {
        recipeListUi.assertNextPageLoadingState()
    }

    fun assertRecipeListChanged() {
        recipeListUi.assertRecipeListChanged()
    }

    fun assertLoadingState() {
        loadingDialogUi.assertVisible()
    }

    fun assertRecipeEmptyListState() {
        recipeListUi.assertRecipeEmptyListState()
    }

    fun clickInputFiled() {
        searchFieldUi.click()
    }

    fun addInput(text: String) {
        searchFieldUi.addInput(text)
    }

    fun clickClearInput() {
        searchFieldUi.clickClear()
    }

    fun clickFirstVariant() {
        searchFieldUi.clickFirstVariant()
    }

    fun deleteLetters(numberOfLetters: Int) {
        searchFieldUi.removeLetters(numberOfLetters)
    }

    fun assertInputEmptyState() {
        searchFieldUi.assertInitialState()
    }

    fun assertInputInsufficientUnfocusedState() {
        searchFieldUi.assertInputInsufficientUnfocusedState()
    }

    fun assertInputInsufficientFocusedState() {
        searchFieldUi.assertInputInsufficientFocusedState()
    }

    fun assertInputSufficientFocusedState() {
        searchFieldUi.assertInputSufficientFocusedState()
    }

    fun assertInputSufficientUnfocusedState() {
        searchFieldUi.assertInputSufficientUnfocusedState()
    }

    fun clickConfirmErrorButton() {
        errorDialogUi.click()
    }

    fun waitTillError() {
        errorDialogUi.waitTillError()
    }

    fun assertErrorDialogState() {
        errorDialogUi.assertErrorDialogState()
    }

    fun assertFirstRecipeIsLiked() {
        recipeListUi.assertFirstRecipeIsLiked()
    }

    fun assertFavoritesEmptyState() {
        recipeListUi.assertFavoritesEmptyState()
    }

    fun assertFavoritesCount(numberOfFavorites: Int) {
        recipeListUi.assertFavoritesCount(numberOfFavorites = numberOfFavorites)
    }
}