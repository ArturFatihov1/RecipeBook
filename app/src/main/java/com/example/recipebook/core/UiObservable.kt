package com.example.recipebook.core

interface UiObservable<T : Any> {
    fun register(observer: (T) -> Unit)

    fun unregister()

    fun postUiState(uiState: T)

    abstract class Abstract<T : Any> : UiObservable<T> {

        private var uiStateCached: T? = null
        private var observerCached: ((T) -> Unit)? = null

        override fun register(observer: (T) -> Unit) {
            observerCached = observer
            if (uiStateCached != null) {
                observerCached!!.invoke(uiStateCached!!)
                uiStateCached = null
            }
        }

        override fun unregister() {
            observerCached = null
        }

        override fun postUiState(uiState: T) {
            if (observerCached == null) {
                uiStateCached = uiState
            } else {
                observerCached!!.invoke(uiState)
                uiStateCached = null
            }
        }
    }
}