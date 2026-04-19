package com.example.recipebook.detail

import java.io.Serializable

interface DetailUiState : Serializable {

    fun update(
        likeButton: UpdateLike,
        headerTextView: UpdateText,
        imageView: UpdateImage,
        ingredientList: UpdateIngredientList,
        instructionList: UpdateInstructionList
    ) = Unit

    data class Initial(
        val title: String,
        val imageUrl: String,
        val ingredients: List<IngredientUiState.Initial>,
        val instructions: List<String>,
        val isLiked: Boolean
    ) : DetailUiState {
        override fun update(
            likeButton: UpdateLike,
            headerTextView: UpdateText,
            imageView: UpdateImage,
            ingredientList: UpdateIngredientList,
            instructionList: UpdateInstructionList
        ) {
            headerTextView.update(title)
            imageView.showImage(imageUrl)
            likeButton.update(isLiked)
            ingredientList.update(ingredients)
            instructionList.update(instructions)
        }
    }

    object IngredientProgressState : DetailUiState {
        override fun update(
            likeButton: UpdateLike,
            headerTextView: UpdateText,
            imageView: UpdateImage,
            ingredientList: UpdateIngredientList,
            instructionList: UpdateInstructionList
        ) {
            ingredientList.showProgress()
        }
    }

    data class IngredientSuccessState(
        val ingredients: List<IngredientUiState.Success>,
    ) : DetailUiState {
        override fun update(
            likeButton: UpdateLike,
            headerTextView: UpdateText,
            imageView: UpdateImage,
            ingredientList: UpdateIngredientList,
            instructionList: UpdateInstructionList
        ) {
            ingredientList.update(ingredients)
        }
    }

    object IngredientErrorState : DetailUiState {
        override fun update(
            likeButton: UpdateLike,
            headerTextView: UpdateText,
            imageView: UpdateImage,
            ingredientList: UpdateIngredientList,
            instructionList: UpdateInstructionList
        ) {
            ingredientList.showError()
        }
    }

    object RecipeLikeState : DetailUiState {
        override fun update(
            likeButton: UpdateLike,
            headerTextView: UpdateText,
            imageView: UpdateImage,
            ingredientList: UpdateIngredientList,
            instructionList: UpdateInstructionList
        ) {
            likeButton.update(isLiked = true)
            LikeToggleUiState.Liked.applyTo(likeButton)
        }
    }

    object RecipeUnLikeState : DetailUiState {
        override fun update(
            likeButton: UpdateLike,
            headerTextView: UpdateText,
            imageView: UpdateImage,
            ingredientList: UpdateIngredientList,
            instructionList: UpdateInstructionList
        ) {
            likeButton.update(isLiked = false)
            LikeToggleUiState.UnLiked.applyTo(likeButton)
        }
    }

    object Leave : DetailUiState
}