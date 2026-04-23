package com.example.recipebook.favorite.data

import androidx.room.withTransaction
import com.example.recipebook.detail.data.Recipe

class FavoriteRepositoryImpl(
    private val database: RecipeBookDatabase
) : FavoriteRepository {

    private val favoriteDao: FavoriteDao
        get() = database.favoriteDao()

    override suspend fun loadFavorites(): List<Recipe> =
        favoriteDao.getFavoritedRecipeEntities()
            .map(FavoriteRecipeStoreMapping::entityToLikedRecipe)

    override suspend fun like(recipe: Recipe) {
        val entity = FavoriteRecipeStoreMapping.recipeToEntity(recipe)
        database.withTransaction {
            favoriteDao.insertRecipe(entity)
            favoriteDao.insertFavorite(FavoriteEntity(recipeId = entity.id))
        }
    }

    override suspend fun unLike(recipeId: String) {
        favoriteDao.deleteFavoriteByRecipeId(recipeId)
    }
}
