package com.example.recipebook.recipelist.presentation

interface RecipeActionListener {
    fun loadMoreRecipes(page: Int)
    fun toggleFavoriteRecipe(id: String)
    fun detailRecipe(recipeId: String)
}