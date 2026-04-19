package com.example.recipebook.recipelist.data

interface RecipeListRepository {
    fun load(page: Int = 0, searchQuery: String = "", amountRecipes: Int = 0): List<RecipeWithSettings>
    fun searchRecipes(searchQuery: String): List<String>
    fun toggleFavoriteRecipe(id: String)
}