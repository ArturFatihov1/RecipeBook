package com.example.recipebook.detail

import android.view.View
import android.widget.LinearLayout
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.example.recipebook.R
import com.example.recipebook.Recipe
import com.example.recipebook.core.BackButtonUi
import com.example.recipebook.core.ImageUI
import com.example.recipebook.core.TitleUi

import org.hamcrest.Matcher

class DetailPage(recipe: Recipe) {

    private val containerId: Int = R.id.detailContainer
    private val containerIdMatcher: Matcher<View> = withParent(withId(containerId))
    private val classTypeMatcher: Matcher<View> =
        withParent(isAssignableFrom(LinearLayout::class.java))

    private val backButton = BackButtonUi(
        viewId = R.id.backButton,
        parentId = R.id.detailHeader,
        containerId = containerId
    )
    private val recipeLikeUi = RecipeLikeUi(
        id = R.id.recipeLike,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )
    private val imageUi = ImageUI(
        id = R.id.detailImage,
        url = recipe.imageUrl,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )
    private val titleUi = TitleUi(
        viewId = R.id.titleHeader,
        parentId = R.id.detailHeader,
        containerId = containerId,
        text = recipe.title,
    )
    private val ingredientsListUi = IngredientsListUi(
        id = R.id.ingredientList,
        ingredientId = R.id.ivIngredient,
        listIngredient = recipe.ingredients,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )
    private val instructionsListUi = InstructionsListUi(
        id = R.id.instructionsList,
        instructions = recipe.instructions,
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )

    fun assertDetailState() {
        backButton.assertVisible()
        recipeLikeUi.assertVisible()
        titleUi.assertVisible()
        imageUi.assertVisible()
        ingredientsListUi.assertVisible()
        instructionsListUi.assertVisible()
        instructionsListUi.assertInstructionsTextVisible()
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