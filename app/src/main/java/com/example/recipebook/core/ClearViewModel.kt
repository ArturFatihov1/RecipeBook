package com.example.recipebook.core

interface ClearViewModel {
    fun clear(viewModelClass: Class<out MyViewModel<*>>)
}