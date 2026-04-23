package com.example.recipebook.detail.data

data class Recipe(
    val id: String,
    val title: String,
    val imageUrl: String,
    val ingredients: List<Ingredient>,
    val instructions: List<String>,
    var isLiked: Boolean
)
