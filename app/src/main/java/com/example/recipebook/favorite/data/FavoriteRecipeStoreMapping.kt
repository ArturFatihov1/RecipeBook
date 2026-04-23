package com.example.recipebook.favorite.data

import com.example.recipebook.detail.data.Recipe

object FavoriteRecipeStoreMapping {

    fun recipeToEntity(recipe: Recipe): RecipeEntity = RecipeEntity(
        id = recipe.id,
        title = recipe.title,
        imageUrl = recipe.imageUrl,
        ingredients = recipe.ingredients,
        instructions = recipe.instructions
    )

    /** Для строк которые уже отфильтрованы через favorites*/
    fun entityToLikedRecipe(entity: RecipeEntity): Recipe = Recipe(
        id = entity.id,
        title = entity.title,
        imageUrl = entity.imageUrl,
        ingredients = entity.ingredients,
        instructions = entity.instructions,
        isLiked = true
    )
}
