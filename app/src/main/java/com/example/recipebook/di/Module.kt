package com.example.recipebook.di

import com.example.recipebook.core.BaseViewModel

interface Module<T: BaseViewModel<*>> {
    fun viewModel(): T
}