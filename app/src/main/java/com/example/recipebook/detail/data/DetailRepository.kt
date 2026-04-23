package com.example.recipebook.detail.data

import java.io.IOException

interface DetailRepository {

    suspend fun recipe(id: Int): Recipe

    suspend fun isFavorite(id: Int): Boolean

    fun likeRecipe(id: Int)

    fun unLikeRecipe(id: Int)

    class Base(
        private val cloudDataSource: DetailCloudDataSource,
        private val dao: DetailDao,
    ) : DetailRepository {

        override suspend fun recipe(id: Int): Recipe {
            try {
                val cacheRecipe = dao.getRecipeById(id)
                val isLiked = dao.isFavorite(id)
                val updateIngredients = cacheRecipe.ingredients.map { ingredient ->
                    val url = cloudDataSource.loadImage(ingredient.name)
                    ingredient.copy(url = url)
                }
                return cacheRecipe.copy(isLiked = isLiked, ingredients = updateIngredients)
            } catch (e: Exception) {
                if (e is IOException)
                    throw NoInternetConnectionException()
                if (e is IllegalArgumentException)
                    throw BackendException(e.message ?: "")
                throw ServiceUnavailable()
            }
        }

        override suspend fun isFavorite(id: Int): Boolean {
            return dao.isFavorite(productId = id)
        }

        override fun likeRecipe(id: Int) {
            dao.insertFavorite(productId = id)
        }

        override fun unLikeRecipe(id: Int) {
            dao.deleteFavorite(productId = id)
        }
    }
}