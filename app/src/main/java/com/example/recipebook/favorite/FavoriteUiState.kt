package com.example.recipebook.favorite

import java.io.Serializable

interface FavoriteUiState : Serializable {

    fun update(
        favoriteList: UpdateFavoriteList,
        emptyVisibility: UpdateFavoriteEmptyVisibility
    ) = Unit

    object Empty : FavoriteUiState {
        override fun update(
            favoriteList: UpdateFavoriteList,
            emptyVisibility: UpdateFavoriteEmptyVisibility
        ) {
            favoriteList.update(emptyList())
            emptyVisibility.showEmpty(true)
        }
    }

    data class FavoriteState(
        private val favorites: List<FavoriteRecipe>
    ) : FavoriteUiState {
        override fun update(
            favoriteList: UpdateFavoriteList,
            emptyVisibility: UpdateFavoriteEmptyVisibility
        ) {
            favoriteList.update(favorites)
            emptyVisibility.showEmpty(false)
        }
    }
}
