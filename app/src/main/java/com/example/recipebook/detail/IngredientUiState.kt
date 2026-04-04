package com.example.recipebook.detail

import java.io.Serializable

interface IngredientUiState : Serializable {

    fun update(
        imageView: UpdateImage,
        titleView: UpdateText,
        weightView: UpdateText
    )

    data class Progress(
        private val title: String,
        private val weight: String
    ) : IngredientUiState {
        override fun update(imageView: UpdateImage, titleView: UpdateText, weightView: UpdateText) {
            imageView.showProgress()
            titleView.update(title)
            weightView.update(weight)
        }
    }

    data class Error(
        private val title: String,
        private val weight: String,
        private val errorIconRes: Int = R.drawable.ic_error
    ) : IngredientUiState {
        override fun update(imageView: UpdateImage, titleView: UpdateText, weightView: UpdateText) {
            imageView.showError(errorIconRes)
            titleView.update(title)
            weightView.update(weight)
        }
    }

    data class Success(
        private val image: String,
        private val title: String,
        private val weight: String
    ) : IngredientUiState {
        override fun update(imageView: UpdateImage, titleView: UpdateText, weightView: UpdateText) {
            imageView.showImage(image)
            titleView.update(title)
            weightView.update(weight)
        }
    }
}
