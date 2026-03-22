package com.example.recipebook.detail

import java.io.Serializable

interface DetailUiState : Serializable {

    fun update(
        likeButton: UpdateLike,
        headerTextView: UpdateText,
        imageView: UpdateImage,
        ingredientList: UpdateIngredientList,
        instructionItemView: UpdateInstructionItem
    ) = Unit

    data class Initial(
        private val title: String,
        private val imageUrl: String,
        private val isLiked: Boolean,
        private val instructions: List<String>
    ) : DetailUiState {
        override fun update(
            likeButton: UpdateLike,
            headerTextView: UpdateText,
            imageView: UpdateImage,
            ingredientList: UpdateIngredientList,
            instructionItemView: UpdateInstructionItem
        ) {
            headerTextView.update(title)
            imageView.showImage(imageUrl)
            likeButton.update(isLiked)
            instructionItemView.update(instructions)
        }
    }

    data class IngredientProgressState(
        private val ingredients: List<IngredientUiState>
    ) : DetailUiState {
        override fun update(
            likeButton: UpdateLike,
            headerTextView: UpdateText,
            imageView: UpdateImage,
            ingredientList: UpdateIngredientList,
            instructionItemView: UpdateInstructionItem
        ) {
            ingredientList.update(ingredients)
        }
    }

    data class IngredientErrorState(
        private val ingredients: List<IngredientUiState>
    ) : DetailUiState {
        override fun update(
            likeButton: UpdateLike,
            headerTextView: UpdateText,
            imageView: UpdateImage,
            ingredientList: UpdateIngredientList,
            instructionItemView: UpdateInstructionItem
        ) {
            ingredientList.update(ingredients)
        }
    }

    data class IngredientSuccessState(
        private val ingredients: List<IngredientUiState>,
    ) : DetailUiState {
        override fun update(
            likeButton: UpdateLike,
            headerTextView: UpdateText,
            imageView: UpdateImage,
            ingredientList: UpdateIngredientList,
            instructionItemView: UpdateInstructionItem
        ) {
            ingredientList.update(ingredients)
        }
    }

    object RecipeLikeState : DetailUiState {
        override fun update(
            likeButton: UpdateLike,
            headerTextView: UpdateText,
            imageView: UpdateImage,
            ingredientList: UpdateIngredientList,
            instructionItemView: UpdateInstructionItem
        ) {
            likeButton.update(isLiked = true)
        }
    }

    object RecipeUnLikeState : DetailUiState {
        override fun update(
            likeButton: UpdateLike,
            headerTextView: UpdateText,
            imageView: UpdateImage,
            ingredientList: UpdateIngredientList,
            instructionItemView: UpdateInstructionItem
        ) {
            likeButton.update(isLiked = false)
        }
    }
}
