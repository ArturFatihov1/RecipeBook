package com.example.recipebook.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

interface BaseViewModel<UiState : Any> {

    abstract class Abstract<UiState : Any>(protected val observable: UiObservable<UiState>) : BaseViewModel<UiState> {

        protected val updateUi: (UiState) -> Unit = { observable.postUiState(it) }

        fun startUpdates(observer: (UiState) -> Unit) {
            observable.register(observer)
        }

        fun stopUpdates() {
            observable.unregister()
        }
    }

    abstract class Async<UiState : Any>(
        observable: UiObservable<UiState>,
        private val runAsync: RunAsync,
    ) : Abstract<UiState>(observable) {
        private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

        protected fun <T : Any> runAsync(heavy: suspend () -> T, ui: (T) -> Unit) {
            runAsync.handleAsync(viewModelScope, heavy, ui)
        }
    }
}