package com.example.recipebook.detail.presentation

import com.example.recipebook.core.UiObservable
import com.example.recipebook.detail.DetailUiState

interface DetailUiObservable : UiObservable<DetailUiState> {

    class Base : UiObservable.Abstract<DetailUiState>(), DetailUiObservable
}