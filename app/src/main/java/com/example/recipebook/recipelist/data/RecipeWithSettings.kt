package com.example.recipebook.recipelist.data

data class RecipeWithSettings(
    val id: String,
    val title: String,
    val imageUrl: String,
    val isFavorite: Boolean,
    val ingredients: List<Ingredient>,
    val instructions: List<String>,
)