package com.example.recipebook.detail.presentation

import com.example.recipebook.core.ClearViewModel
import com.example.recipebook.core.RunAsync
import com.example.recipebook.detail.DetailUiState
import com.example.recipebook.detail.IngredientUiState
import com.example.recipebook.detail.data.DetailRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class DetailViewModel(
    private val repository: DetailRepository,
    private val runAsync: RunAsync,
    private val clearViewModel: ClearViewModel,
    private val uiObservable: DetailUiObservable
) {

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val uiUpdate: (DetailUiState) -> Unit = {
        uiObservable.postUiState(it)
    }

    fun init(id: Int) {
        runAsync.handleAsync(viewModelScope, {
            val data = repository.recipe(id = id)

            val initialIngredients = data.ingredients.map { ingredient ->
                IngredientUiState.Initial(
                    name = ingredient.name,
                    measure = ingredient.measure
                )
            }

            DetailUiState.Initial(
                title = data.title,
                imageUrl = data.imageUrl,
                ingredients = initialIngredients,
                instructions = data.instructions,
                isLiked = data.isLiked
            )
        }, uiUpdate)
    }

    fun progress(id: Int) {
        uiObservable.postUiState(DetailUiState.Progress)

        runAsync.handleAsync(
            viewModelScope, {
                try {
                    val data = repository.recipe(id = id)
                    DetailUiState.IngredientSuccessState(
                        ingredients = data.ingredients.map { ingredient ->
                            IngredientUiState.Success(
                                imageUrl = ingredient.url ?: "",
                            )
                        }
                    )
                } catch (e: Exception) {
                    DetailUiState.IngredientErrorState
                }
            }, uiUpdate
        )
    }

    fun like(id: Int) {
        runAsync.handleAsync(viewModelScope, {
            repository.likeRecipe(id)
            DetailUiState.RecipeLikeState
        }, uiUpdate)
    }

    fun unLike(id: Int) {
        runAsync.handleAsync(viewModelScope, {
            repository.unLikeRecipe(id)
            DetailUiState.RecipeUnLikeState
        }, uiUpdate)
    }

    fun back() {
        runAsync.handleAsync(viewModelScope, {
            repository.clear()
            clearViewModel.clear(DetailViewModel::class.java)
            DetailUiState.Leave
        }, uiUpdate)
    }
}