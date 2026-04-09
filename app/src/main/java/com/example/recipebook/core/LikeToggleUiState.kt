package com.example.recipebook.core

import java.io.Serializable

interface LikeToggleUiState : Serializable {

    fun applyTo(like: UpdateLike) = Unit

    object Liked : LikeToggleUiState {
        override fun applyTo(like: UpdateLike) {
            like.update(true)
        }
    }

    object UnLiked : LikeToggleUiState {
        override fun applyTo(like: UpdateLike) {
            like.update(false)
        }
    }
}
