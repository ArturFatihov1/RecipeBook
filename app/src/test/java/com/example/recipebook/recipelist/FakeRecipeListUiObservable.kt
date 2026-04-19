package com.example.recipebook.recipelist

import com.example.recipebook.core.FakeUiObservable

interface FakeRecipeListUiObservable : RecipeListUiObservable {
    class Base : FakeUiObservable<RecipeListUiState>, FakeRecipeListUiObservable
}