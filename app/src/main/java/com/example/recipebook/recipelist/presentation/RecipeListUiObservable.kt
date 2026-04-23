package com.example.recipebook.recipelist.presentation

import com.example.recipebook.core.UiObservable

interface RecipeListUiObservable : UiObservable<RecipeListUiState> {
    class Base : UiObservable.Abstract<RecipeListUiState>(), RecipeListUiObservable
}