package com.example.recipebook.di

import com.example.recipebook.core.BaseViewModel
import com.example.recipebook.core.Core

abstract class AbstractProvideViewModel(
    protected val core: Core,
    private val nextChain: ProvideViewModel,
    private val viewModelClass: Class<out BaseViewModel<*>>
) : ProvideViewModel {

    override fun <S : Any, T : BaseViewModel<S>> makeViewModel(clasz: Class<out T>): T {
        return if (clasz == viewModelClass) {
            module().viewModel() as T
        } else {
            nextChain.makeViewModel(clasz)
        }
    }

    protected abstract fun module(): Module<*>
}