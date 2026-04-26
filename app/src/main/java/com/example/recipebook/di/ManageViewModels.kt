package com.example.recipebook.di

import com.example.recipebook.core.BaseViewModel
import com.example.recipebook.core.ClearViewModel

interface ManageViewModels : ProvideViewModel, ClearViewModel {

    class Factory(
        private val make: ProvideViewModel.Make
    ) : ManageViewModels {

        private val mapViewModels = mutableMapOf<Class<out BaseViewModel<*>>, BaseViewModel<*>?>()

        override fun <S : Any, T : BaseViewModel<S>> makeViewModel(clasz: Class<out T>): T =
            if (mapViewModels[clasz] == null) {
                val viewModel = make.makeViewModel(clasz)
                mapViewModels[clasz] = viewModel
                viewModel
            } else {
                mapViewModels[clasz]
            } as T

        override fun clear(viewModelClass: Class<out BaseViewModel<*>>) {
            mapViewModels[viewModelClass] = null
        }
    }
}