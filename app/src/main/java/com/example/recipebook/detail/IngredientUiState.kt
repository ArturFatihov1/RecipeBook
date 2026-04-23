package com.example.recipebook.detail

import java.io.Serializable

sealed interface IngredientUiState : Serializable {

    data class Initial(
        val name: String,
        val measure: String
    ) : IngredientUiState

    data class Success(
        val imageUrl: String
    ) : IngredientUiState
}