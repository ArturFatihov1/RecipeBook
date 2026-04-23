package com.example.recipebook

import android.app.Application
import androidx.room.Room
import com.example.recipebook.favorite.data.FavoriteRepository
import com.example.recipebook.favorite.data.FavoriteRepositoryImpl
import com.example.recipebook.favorite.data.RecipeBookDatabase

class RecipeBookApplication : Application() {

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            RecipeBookDatabase::class.java,
            "recipe_book.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    val favoriteRepository: FavoriteRepository by lazy {
        FavoriteRepositoryImpl(database)
    }
}
