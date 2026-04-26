package com.example.recipebook.di

import com.example.recipebook.core.BaseViewModel
import com.example.recipebook.core.Core
import com.example.recipebook.recipelist.di.ProvideRecipeListViewModel

interface ProvideViewModel {
    fun <S : Any, T : BaseViewModel<S>> makeViewModel(clasz: Class<out T>): T

    class Make(
        core: Core
    ) : ProvideViewModel {

        private var chain: ProvideViewModel = Error()

        init {
            chain = Error()
            chain = ProvideRecipeListViewModel(core, chain)
        }

        override fun <S : Any, T : BaseViewModel<S>> makeViewModel(clasz: Class<out T>): T =
            chain.makeViewModel(clasz)
    }

    class Error() : ProvideViewModel {

        override fun <S : Any, T : BaseViewModel<S>> makeViewModel(clasz: Class<out T>): T =
            throw IllegalStateException("you forget add to chain $clasz")
    }
}