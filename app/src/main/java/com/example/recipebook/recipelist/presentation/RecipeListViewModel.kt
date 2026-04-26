package com.example.recipebook.recipelist.presentation

import com.example.recipebook.core.BaseViewModel
import com.example.recipebook.core.ClearViewModel
import com.example.recipebook.core.Core
import com.example.recipebook.core.HandleError
import com.example.recipebook.core.HandleErrorState
import com.example.recipebook.core.RunAsync
import com.example.recipebook.recipelist.data.RecipeListRepository

class RecipeListViewModel(
    observable: RecipeListUiObservable,
    runAsync: RunAsync,
    private val repository: RecipeListRepository,
    private val handleError: HandleError<HandleErrorState>,
    private val clearViewModel: ClearViewModel,
) : BaseViewModel.Async<RecipeListUiState>(observable, runAsync), RecipeActionListener {

    fun load(query: String = "") {
        observable.postUiState(RecipeListUiState.LoadingState)
        runAsync({
            try {
                val recipes = repository.load(searchQuery = query, amountRecipes = Core.AMOUNT_RECIPES)
                RecipeListUiState.RecipeListState(recipes = recipes)
            } catch (e: Exception) {
                val error = handleError.handle(e)
                RecipeListUiState.ErrorState(error)
            }
        }, updateUi)
    }

    fun handleUserInput(input: String) {
        if (input.length >= 3) {
            observable.postUiState(RecipeListUiState.RefreshState)
        } else {
            observable.postUiState(RecipeListUiState.InputInsufficientFocusedState)
        }
    }

    fun searchVariants(input: String) {
        runAsync({
            RecipeListUiState.InputSufficientFocusedState(variants = repository.searchRecipes(searchQuery = input))
        }, updateUi)
    }

    fun chooseSearchVariant(variant: String) {
        observable.postUiState(RecipeListUiState.RefreshState)
        runAsync({ RecipeListUiState.RecipeListState(recipes = repository.load(searchQuery = variant)) }, updateUi)
    }

    fun updateRecipes() {
        observable.postUiState(RecipeListUiState.RefreshState)
        runAsync({ RecipeListUiState.RecipeListState(recipes = repository.load()) }, updateUi)
    }

    fun favoriteRecipes() {
        runAsync({
            clearViewModel.clear(RecipeListViewModel::class.java)
            RecipeListUiState.Favorites
        }, updateUi)
    }

    override fun toggleFavoriteRecipe(id: String) {
        runAsync({
            repository.toggleFavoriteRecipe(id)
            RecipeListUiState.RecipeListState(recipes = repository.load())
        }, updateUi)
    }

    override fun detailRecipe(recipeId: String) {
        runAsync({
            clearViewModel.clear(RecipeListViewModel::class.java)
            RecipeListUiState.Detail
        }, updateUi)
    }

    override fun loadMoreRecipes(page: Int) {
        observable.postUiState(RecipeListUiState.NextPageState)
        runAsync({ RecipeListUiState.RecipeListState(recipes = repository.load(page = page)) }, updateUi)
    }
}