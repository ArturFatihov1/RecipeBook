package com.example.recipebook.favorite

import java.io.Serializable

data class FavoriteRecipe(
    val id: Int,
    val title: String
) : Serializable
