package com.example.recipebook.detail.core

interface UiObservable<T : Any> {

    fun register(observer: (T) -> Unit)

    fun unregister()

    fun postUiState(uiState: T)
}
