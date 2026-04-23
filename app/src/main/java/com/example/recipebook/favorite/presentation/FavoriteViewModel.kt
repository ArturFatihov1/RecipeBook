package com.example.recipebook.favorite.presentation

import com.example.recipebook.core.RunAsync
import com.example.recipebook.detail.data.Recipe
import com.example.recipebook.favorite.FavoriteUiState
import com.example.recipebook.favorite.data.FavoriteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class FavoriteViewModel(
    private val repository: FavoriteRepository,
    private val runAsync: RunAsync,
    observable: FavoriteUiObservable
) {

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val uiUpdate: (FavoriteUiState) -> Unit = { observable.postUiState(it) }

    fun load() {
        uiUpdate(FavoriteUiState.Empty)
        runAsync.handleAsync(viewModelScope, {
            FavoriteUiState.FavoriteState(favorites = repository.loadFavorites())
        }, uiUpdate)
    }

    fun like(recipe: Recipe) {
        runAsync.handleAsync(viewModelScope, {
            repository.like(recipe)
            FavoriteUiState.FavoriteState(favorites = repository.loadFavorites())
        }, uiUpdate)
    }

    fun unLike(recipeId: String) {
        runAsync.handleAsync(viewModelScope, {
            repository.unLike(recipeId)
            FavoriteUiState.FavoriteState(favorites = repository.loadFavorites())
        }, uiUpdate)
    }
}
