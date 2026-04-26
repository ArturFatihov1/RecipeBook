package com.example.recipebook.core

class Core(val clearViewModel: ClearViewModel) {
    val runAsync: RunAsync = RunAsync.Base()

    companion object {
        const val AMOUNT_RECIPES = 10
        const val MIN_SEARCH_LENGTH = 3
    }
}