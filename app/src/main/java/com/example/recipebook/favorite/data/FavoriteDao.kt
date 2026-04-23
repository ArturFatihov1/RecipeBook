package com.example.recipebook.favorite.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {

    @Query(
        """
        SELECT r.* FROM recipes AS r
        INNER JOIN favorites AS f ON r.id = f.recipe_id
        ORDER BY r.title COLLATE NOCASE ASC
        """
    )
    suspend fun getFavoritedRecipeEntities(): List<RecipeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE recipe_id = :recipeId")
    suspend fun deleteFavoriteByRecipeId(recipeId: String)
}
