package com.example.recipebook.recipelist.di

import com.example.recipebook.core.Core
import com.example.recipebook.core.HandleError
import com.example.recipebook.di.AbstractProvideViewModel
import com.example.recipebook.di.Module
import com.example.recipebook.di.ProvideViewModel
import com.example.recipebook.recipelist.data.RecipeListRepository
import com.example.recipebook.recipelist.presentation.RecipeListUiObservable
import com.example.recipebook.recipelist.presentation.RecipeListViewModel

class RecipeListModule(private val core: Core) : Module<RecipeListViewModel> {

    override fun viewModel(): RecipeListViewModel {
        return RecipeListViewModel(
            observable = RecipeListUiObservable.Base(),
            runAsync = core.runAsync,
            repository = RecipeListRepository.Fake(),
            handleError = HandleError.DomainToUi(),
            clearViewModel = core.clearViewModel
        )
    }
}

class ProvideRecipeListViewModel(
    core: Core,
    next: ProvideViewModel
) : AbstractProvideViewModel(
    core,
    next,
    RecipeListViewModel::class.java
) {
    override fun module(): Module<*> = RecipeListModule(core)
}