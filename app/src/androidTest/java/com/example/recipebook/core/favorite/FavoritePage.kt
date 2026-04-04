package com.example.recipebook.core.favorite

import android.view.View
import android.widget.LinearLayout
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.example.recipebook.R
import com.example.recipebook.Recipe
import com.example.recipebook.core.BackButtonUi
import com.example.recipebook.core.ImageUI
import com.example.recipebook.core.ScrollableListUi
import com.example.recipebook.core.TitleUi
import com.example.recipebook.detail.IngredientsListUi
import com.example.recipebook.detail.RecipeLikeUi
import org.hamcrest.Matcher

class FavoritePage(recipes: List<Recipe>) {

    private val containerIdMatcher: Matcher<View> = withParent(withId(R.id.favoriteContainer))
    private val classTypeMatcher: Matcher<View> =
        withParent(isAssignableFrom(LinearLayout::class.java))

    private val firstRecipe = recipes.first()

    private val backButton = BackButtonUi(
        id = R.id.backButton,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )

    private val recipeListUi = ScrollableListUi(
        id = R.id.scrList,
        items = recipes,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher,
    )

    private val imageUi = ImageUI(
        id = R.id.detailImage,
        url = firstRecipe.imageUrl,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher

    )

    private val recipeLikeUi = RecipeLikeUi(
        id = R.id.recipeLike,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher

    )

    private val titleUi = TitleUi(
        id = R.id.titleHeader,
        text = firstRecipe.title,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher

    )

    private val ingredientsListUi = IngredientsListUi(
        id = R.id.ingredientList,
        ingredientId = R.id.ivIngredient,
        listIngredient = firstRecipe.ingredients,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )

    fun assertFavoritesState(count: Int) {
        backButton.assertVisible()
        imageUi.assertVisibleImage()
        recipeLikeUi.isLiked()
        titleUi.assertVisible()
        ingredientsListUi.assertIngredientsCount()
        recipeListUi.assertCount(count)
    }

    fun assertFavoritesEmptyState() {
        recipeListUi.assertFavoritesEmptyState()
    }

    fun assertRecipeEmptyListState() {
        recipeListUi.assertRecipeEmptyListState()
    }

    fun clickFirstRecipe() {
        recipeListUi.clickFirstRecipe()
    }

    fun clickBack() {
        backButton.click()
    }

    fun clickUnLike(recipeId: Int) {
        recipeListUi.clickUnLike(recipeId = recipeId)
    }
}