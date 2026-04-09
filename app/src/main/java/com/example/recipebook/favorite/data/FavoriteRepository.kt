package com.example.recipebook.favorite.data

import com.example.recipebook.detail.data.Recipe

interface FavoriteRepository {

    suspend fun loadFavorites(): List<Recipe>

    suspend fun like(recipe: Recipe)

    suspend fun unLike(recipeId: String)
}
