package com.example.recipebook.detail

interface UpdateIngredientList {

    fun update(newList: List<IngredientUiState>)

    fun showProgress()

    fun showError()
}