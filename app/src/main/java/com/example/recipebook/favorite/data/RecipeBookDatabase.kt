package com.example.recipebook.favorite.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [RecipeEntity::class, FavoriteEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(RecipeTypeConverters::class)
abstract class RecipeBookDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao
}
