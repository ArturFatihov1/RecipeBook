package com.example.recipebook.core

class FakeFragment<UiState: Any> : (UiState) -> Unit {
    val stateList = mutableListOf<UiState>()

    override fun invoke(state: UiState) {
        stateList.add(state)
    }
}