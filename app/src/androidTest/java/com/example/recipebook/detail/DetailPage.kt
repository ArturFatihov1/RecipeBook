package com.example.recipebook.detail

import android.view.View
import android.widget.LinearLayout
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.example.recipebook.R
import com.example.recipebook.Recipe
import org.hamcrest.Matcher

class DetailPage(recipe: Recipe) {

    private val containerIdMatcher: Matcher<View> = withParent(withId(R.id.rootLayout))
    private val classTypeMatcher: Matcher<View> =
        withParent(isAssignableFrom(LinearLayout::class.java))

    private val backButton = BackButtonUi(
        id = R.id.backButton,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )
    private val recipeLikeUi = RecipeLikeUi(
        id = R.id.recipeLike,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )
    private val imageUi = ImageUI(
        url = recipe.imageUrl,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )
    private val titleUi = TitleUi(
        text = recipe.title,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )
    private val ingredientsListUi = IngredientsListUi(
        listIngredient = recipe.ingredients,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )
    private val instructionsListUi = InstructionsListUi(
        instructions = recipe.instructions,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )

    fun assertDetailState() {
        backButton.assertVisible()
        recipeLikeUi.assertVisible()
        titleUi.assertTextVisible()
        imageUi.assertVisible()
        ingredientsListUi.assertVisible()
        instructionsListUi.assertVisible()
    }

    fun assertIngredientProgressState() {
        ingredientsListUi.assertProgressUi()
    }

    fun waitForLoadingIngredient() {
        ingredientsListUi.waitTillVisible()
    }

    fun assertIngredientErrorImageState() {
        ingredientsListUi.assertErrorUi()
    }

    fun clickBack() {
        backButton.click()
    }

    fun assertIngredientSuccessState() {
        ingredientsListUi.assertSuccessUi()
    }

    fun clickOnLike() {
        recipeLikeUi.click()
    }

    fun assertRecipeSettingsStateLiked() {
        recipeLikeUi.isLiked()
    }

    fun assertRecipeSettingsStateNotLiked() {
        recipeLikeUi.isNotLiked()
    }
}