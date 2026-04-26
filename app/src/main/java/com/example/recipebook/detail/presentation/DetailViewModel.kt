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
    private var isLiked: Boolean = false

    private val uiUpdate: (DetailUiState) -> Unit = { state ->
        if (state is DetailUiState.Initial) {
            isLiked = state.isLiked
        }
        uiObservable.postUiState(state)
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

    fun toggleLike(id: Int) {
        runAsync.handleAsync(viewModelScope, {
            if (isLiked) {
                repository.unLikeRecipe(id)
                isLiked = false
                DetailUiState.RecipeUnLikeState
            } else {
                repository.likeRecipe(id)
                isLiked = true
                DetailUiState.RecipeLikeState
            }
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