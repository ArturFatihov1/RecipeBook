package com.example.recipebook

import android.app.Application
import com.example.recipebook.core.BaseViewModel
import com.example.recipebook.core.ClearViewModel
import com.example.recipebook.core.Core
import com.example.recipebook.di.ManageViewModels
import com.example.recipebook.di.ProvideViewModel

class RecipeBookApp : Application(), ProvideViewModel {
    lateinit var factory: ManageViewModels.Factory

    override fun onCreate() {
        super.onCreate()
        val core = Core(
            this,
            object : ClearViewModel {
                override fun clear(viewModelClass: Class<out BaseViewModel<*>>) {
                    factory.clear(viewModelClass)
                }
            }
        )
        val make = ProvideViewModel.Make(core)
        factory = ManageViewModels.Factory(make = make)
    }

    override fun <S : Any, T : BaseViewModel<S>> makeViewModel(clasz: Class<out T>): T = factory.makeViewModel(clasz)
}