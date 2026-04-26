package com.example.recipebook.recipelist.presentation.search

import java.io.Serializable

interface SearchUiState : Serializable {

    fun update(updateLayout: UpdateSearch)

    object Insufficient : SearchUiState {
        override fun update(updateLayout: UpdateSearch) {
            this.update(updateLayout)
            updateLayout.update(emptyList())
        }
    }

    data class Sufficient(private val variants: List<String>) : SearchUiState {
        override fun update(updateLayout: UpdateSearch) {
            this.update(updateLayout)
            updateLayout.update(variants)
        }
    }
}