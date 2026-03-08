package com.example.recipebook

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.recipebook.FakeRecipes.firstPageRecipes
import com.example.recipebook.FakeRecipes.searchedRecipes
import com.example.recipebook.FakeRecipes.secondPageRecipes
import com.example.recipebook.FakeRecipes.thirdPageRecipes
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScenarioTest {

    lateinit var recipeListPage: RecipeListPage

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        recipeListPage = RecipeListPage(recipes = emptyList<Recipe>())
    }

    @Test
    fun get_recipes_at_start() {
        activityScenarioRule.doWithRecreate { recipeListPage.assertLoadingState() }

        activityScenarioRule.waitTillError()
        activityScenarioRule.doWithRecreate { recipeListPage.ErrorDialogState() }

        recipeListPage.clickConfirmErrorButton()
        activityScenarioRule.doWithRecreate { recipeListPage.assertRecipeEmptyListState() }

        recipeListPage.refreshRecipes()
        activityScenarioRule.doWithRecreate { recipeListPage.assertRefreshState() }
        recipeListPage.waitForRecipesListUpdate()

        recipeListPage = RecipeListPage(firstPageRecipes)
        activityScenarioRule.doWithRecreate { recipeListPage.assertRecipeListState() }
    }

    @Test
    fun get_recipes_refresh_get_next_page_search_recipe_add_favourites() {
        get_recipes_at_start()
        activityScenarioRule.doWithRecreate {
            recipeListPage.assertRecipeListState()
            recipeListPage.assertInputEmptyState()
        }

        recipeListPage.refreshRecipes()
        activityScenarioRule.doWithRecreate {
            recipeListPage.assertRefreshState()
            recipeListPage.assertRecipeListState()
            recipeListPage.assertInputEmptyState()
        }
        recipeListPage.waitForRecipesListUpdate()
        activityScenarioRule.doWithRecreate {
            recipeListPage.assertRecipeListState()
            recipeListPage.assertInputEmptyState()
        }

        recipeListPage.scrollToNextPage()
        activityScenarioRule.doWithRecreate {
            recipeListPage.assertNextPageState()
            recipeListPage.assertInputEmptyState()
        }
        recipeListPage.waitForRecipesListUpdate()

        recipeListPage.addRecipes(secondPageRecipes)
        activityScenarioRule.doWithRecreate {
            recipeListPage.assertRecipeListIncreased()
            recipeListPage.assertInputEmptyState()
        }

        recipeListPage.scrollToNextPage()
        activityScenarioRule.doWithRecreate {
            recipeListPage.assertNextPageState()
            recipeListPage.assertInputEmptyState()
        }
        recipeListPage.waitForRecipesListUpdate()

        recipeListPage.addRecipes(thirdPageRecipes)
        activityScenarioRule.doWithRecreate {
            recipeListPage.assertRecipeListIncreased()
            recipeListPage.assertInputEmptyState()
        }

        recipeListPage.addInput(text = "sh")
        activityScenarioRule.doWithRecreate(recipeListPage::assertInputFocusedInsufficientState)

        recipeListPage.addInput(text = "a")
        activityScenarioRule.doWithRecreate(recipeListPage::assertSufficientFocusedInputState)

        recipeListPage.clickFirstVariant()
        activityScenarioRule.doWithRecreate {
            recipeListPage.assertRefreshState()
            recipeListPage.assertSufficientUnfocusedInputState()
        }
        recipeListPage.waitForRecipesListUpdate()

        val searchedRecipes = searchedRecipes("sha")
        recipeListPage = RecipeListPage(searchedRecipes)
        activityScenarioRule.doWithRecreate {
            recipeListPage.assertRecipeListChanged()
            recipeListPage.assertInputSufficientUnfocusedState()
        }

        recipeListPage.clickLikeOnFirstRecipe()
        activityScenarioRule.doWithRecreate(recipeListPage::assertFirstRecipeIsLiked)
    }

    private fun ActivityScenarioRule<*>.doWithRecreate(block: () -> Unit) {
        block.invoke()
        this.scenario.recreate()
        block.invoke()
    }
}

data class Recipe(
    val id: String,
    val title: String,
    val imageUrl: String,
    val ingredients: List<Ingredient>,
    val instructions: List<String>,
)

data class Ingredient(
    val id: String,
    val name: String,
    val measure: String,
)